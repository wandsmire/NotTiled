#!/usr/bin/env python3
"""Generate basic_swatch.png — one row of 8 basic colors."""

from pathlib import Path

try:
    from PIL import Image
except ImportError:
    Image = None

COLORS = [
    (0, 0, 0),        # black
    (255, 255, 255),  # white
    (255, 0, 0),      # red
    (255, 255, 0),    # yellow
    (0, 255, 0),      # green
    (0, 255, 255),    # cyan
    (0, 0, 255),      # blue
    (255, 0, 255),    # magenta
]


def main() -> None:
    width, height = 8, 1
    out = Path(__file__).resolve().parent.parent / "basic_swatch.png"

    if Image is not None:
        img = Image.new("RGB", (width, height))
        img.putdata(COLORS)
        img.save(out)
    else:
        import struct
        import zlib

        def chunk(tag: bytes, data: bytes) -> bytes:
            return (
                struct.pack(">I", len(data))
                + tag
                + data
                + struct.pack(">I", zlib.crc32(tag + data) & 0xFFFFFFFF)
            )

        raw = b"\x00" + bytes(c for px in COLORS for c in px)
        png = (
            b"\x89PNG\r\n\x1a\n"
            + chunk(b"IHDR", struct.pack(">IIBBBBB", width, height, 8, 2, 0, 0, 0))
            + chunk(b"IDAT", zlib.compress(raw, 9))
            + chunk(b"IEND", b"")
        )
        out.write_bytes(png)

    print(f"Wrote {out} ({width}x{height})")
    print(COLORS)


if __name__ == "__main__":
    main()
