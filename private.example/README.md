# Local-only files (never uploaded to GitHub)

Copy this folder to `private/` at the project root and fill in your values:

```bash
cp -r private.example private
# edit private/local.properties
./setup_private.sh
```

## What goes in `private/`

| Path | Purpose |
|------|---------|
| `local.properties` | Android SDK path + release signing passwords |
| `keystore/` | Play Store / release signing keys |
| `export.keystore` | In-app export signing (copy or generate separately) |
| `google-services.json` | Optional — only if you build the Play Store flavor with Firebase |
| `NotTiled-CollabServer.jar` | Optional — built by `./start_collab_server.sh` |
| `scratch/` | Optional — your local experiments and logs |

**Back up the entire `private/` folder** (USB, cloud, etc.). If you lose `keystore/`, you cannot update the same Play Store app without Google's help.
