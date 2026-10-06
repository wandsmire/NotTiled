#!/usr/bin/env python3
"""Upload a signed AAB to a Google Play track via the Android Publisher API."""
import argparse
import base64
import json
import re
import sys
import time
from pathlib import Path

import requests
from cryptography.hazmat.primitives import hashes, serialization
from cryptography.hazmat.primitives.asymmetric import padding

PACKAGE = "com.mirwanda.nottiled"
API = "https://androidpublisher.googleapis.com/androidpublisher/v3/applications"
UPLOAD_API = "https://androidpublisher.googleapis.com/upload/androidpublisher/v3/applications"
SCOPE = "https://www.googleapis.com/auth/androidpublisher"
ROOT = Path(__file__).resolve().parent


def fail(msg):
    print(f"ERROR: {msg}", file=sys.stderr)
    sys.exit(1)


def b64url(data):
    return base64.urlsafe_b64encode(data).rstrip(b"=").decode()


def access_token(key_file):
    sa = json.loads(Path(key_file).read_text())
    now = int(time.time())
    header = b64url(json.dumps({"alg": "RS256", "typ": "JWT"}).encode())
    claims = b64url(json.dumps({
        "iss": sa["client_email"], "scope": SCOPE, "aud": sa["token_uri"],
        "iat": now, "exp": now + 3600,
    }).encode())
    key = serialization.load_pem_private_key(sa["private_key"].encode(), password=None)
    signature = key.sign(f"{header}.{claims}".encode(), padding.PKCS1v15(), hashes.SHA256())
    jwt = f"{header}.{claims}.{b64url(signature)}"
    r = requests.post(sa["token_uri"], timeout=30, data={
        "grant_type": "urn:ietf:params:oauth:grant-type:jwt-bearer", "assertion": jwt})
    if r.status_code != 200:
        fail(f"could not get an access token ({r.status_code}): {r.text}")
    return r.json()["access_token"]


def gradle_version_code():
    m = re.search(r"versionCode\s+(\d+)", (ROOT / "android/build.gradle").read_text())
    if not m:
        fail("versionCode not found in android/build.gradle")
    return int(m.group(1))


def check(r, what):
    if r.status_code >= 300:
        fail(f"{what} failed ({r.status_code}): {r.text}")
    return r.json() if r.text else {}


def main():
    ap = argparse.ArgumentParser(description=__doc__)
    ap.add_argument("aab", help="signed .aab to upload")
    ap.add_argument("--track", default="internal", help="internal (default), alpha, beta or production")
    ap.add_argument("--notes", default="", help="release notes text")
    ap.add_argument("--notes-file", help="read release notes from this file")
    ap.add_argument("--lang", default="en-US", help="language of the release notes")
    ap.add_argument("--draft", action="store_true", help="create the release as a draft instead of rolling it out")
    ap.add_argument("--key", default=str(ROOT / "private/play-service-account.json"), help="service account JSON key")
    ap.add_argument("--dry-run", action="store_true", help="check everything locally, don't contact Google")
    args = ap.parse_args()

    aab = Path(args.aab)
    if not aab.is_file():
        fail(f"{aab} not found")
    if not Path(args.key).is_file():
        fail(f"service account key not found at {args.key} (see BUILD.md, 'Publishing to Google Play')")
    notes = Path(args.notes_file).read_text().strip() if args.notes_file else args.notes.strip()
    version_code = gradle_version_code()
    print(f"Package {PACKAGE}, versionCode {version_code}, track '{args.track}', file {aab.name} "
          f"({aab.stat().st_size // 1048576} MB)")
    if args.dry_run:
        print(f"Service account: {json.loads(Path(args.key).read_text())['client_email']}")
        print("Dry run OK: nothing uploaded.")
        return

    s = requests.Session()
    s.headers["Authorization"] = f"Bearer {access_token(args.key)}"
    base = f"{API}/{PACKAGE}"

    edit_id = check(s.post(f"{base}/edits", timeout=60), "creating edit")["id"]

    used = []
    for t in check(s.get(f"{base}/edits/{edit_id}/tracks", timeout=60), "listing tracks").get("tracks", []):
        for rel in t.get("releases", []):
            used += [int(v) for v in rel.get("versionCodes", [])]
    if used and version_code <= max(used):
        s.delete(f"{base}/edits/{edit_id}", timeout=60)
        fail(f"versionCode {version_code} is not higher than the highest on Play ({max(used)}). "
             f"Bump versionCode in android/build.gradle and rebuild.")

    print("Uploading bundle...")
    with aab.open("rb") as f:
        r = s.post(f"{UPLOAD_API}/{PACKAGE}/edits/{edit_id}/bundles", params={"uploadType": "media"},
                   headers={"Content-Type": "application/octet-stream"}, data=f, timeout=900)
    uploaded = check(r, "uploading bundle")["versionCode"]

    release = {"versionCodes": [str(uploaded)], "status": "draft" if args.draft else "completed"}
    if notes:
        release["releaseNotes"] = [{"language": args.lang, "text": notes[:500]}]
    check(s.put(f"{base}/edits/{edit_id}/tracks/{args.track}", timeout=60,
                json={"track": args.track, "releases": [release]}), f"assigning to track '{args.track}'")

    r = s.post(f"{base}/edits/{edit_id}:commit", timeout=120)
    if r.status_code == 400 and "changesNotSentForReview" in r.text:
        r = s.post(f"{base}/edits/{edit_id}:commit", params={"changesNotSentForReview": "true"}, timeout=120)
        check(r, "committing edit")
        print(f"Uploaded versionCode {uploaded} to '{args.track}'. Play requires you to send it for review "
              f"manually: Play Console -> Publishing overview -> Send for review.")
        return
    check(r, "committing edit")
    print(f"Done: versionCode {uploaded} is on the '{args.track}' track"
          f"{' as a draft' if args.draft else ''}.")


if __name__ == "__main__":
    main()
