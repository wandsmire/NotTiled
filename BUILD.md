# How to Build APK and AAB for NotTiled

This guide outlines the commands and configuration required to build the NotTiled Android application (`.apk` and `.aab` packages) from the terminal.

---

## 1. Prerequisites

### Java Development Kit (JDK)
Ensure you are using **JDK 17** to compile the project. You can define the `JAVA_HOME` environment variable:
```bash
export JAVA_HOME=/usr/lib/jvm/java-1.17.0-openjdk-amd64
```

### Android SDK Path
Gradle needs to know where the Android SDK is located. You can define this in two ways:
1. Define the `ANDROID_HOME` environment variable:
   ```bash
   export ANDROID_HOME=/home/reza/Android/sdk
   ```
2. Or, create a file named `local.properties` in the root of the project:
   ```properties
   sdk.dir=/home/reza/Android/sdk
   ```

---

## 2. Sample assets

Edit bundled samples under **`samples/sample/`** (not the zip). `build_debug.sh`, `build_release.sh`, and `build_aab.sh` pack them into `android/assets/sample.zip` before Gradle runs.

The **New file** picker (“What do you want to create?”) is configured in **`newfile-kinds/newfile-kinds.json`** (copied to `android/assets/newfile-kinds.json` at build time). See `newfile-kinds/README.md`.

---

## 3. Build Commands

Run these Gradle commands from the root directory of the project.

### Build APK (Android Package)
- **Debug APK** (for testing on device/emulator):
  ```bash
  ./gradlew :android:assembleDebug
  ```
- **Release APK** (unsigned or signed):
  ```bash
  ./gradlew :android:assembleRelease
  ```

### Build AAB (Android App Bundle - for Play Store upload)
- **Debug App Bundle**:
  ```bash
  ./gradlew :android:bundleDebug
  ```
- **Release App Bundle**:
  ```bash
  ./gradlew :android:bundleRelease
  ```

---

## 3. Output Locations

The project is configured to compile output files outside of the project root directory:
- **Build Output Directory:** `../NotTiled_build/android/outputs/`

To make this convenient, the helper scripts (`build_apk.sh` and `build_aab.sh`) automatically create an **`out/`** directory in the project root and copy the built package there:

* **Using the helper scripts:**
  - APK: `out/android-debug.apk`
  - AAB: `out/android-release.aab`

* **Direct built files (located outside the project directory):**
  - APK: `../NotTiled_build/android/outputs/apk/debug/android-debug.apk`
  - AAB: `../NotTiled_build/android/outputs/bundle/release/android-release.aab`

---

## 4. Signing Release Packages (Optional)

To sign your release packages for deployment or Play Store upload directly from Gradle, configure your signing details:

1. Generate a upload keystore (if you don't have one):
   ```bash
   keytool -genkey -v -keystore my-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-key-alias
   ```
2. Copy `local.properties.example` to `local.properties` (gitignored) and set your credentials:
   ```properties
   sdk.dir=/path/to/Android/sdk
   RELEASE_STORE_FILE=keystore/keystore
   RELEASE_STORE_PASSWORD=your_store_password
   RELEASE_KEY_ALIAS=your_key_alias
   RELEASE_KEY_PASSWORD=your_key_password
   ```
   Paths in `RELEASE_STORE_FILE` are relative to the project root. `android/build.gradle` reads these automatically.

3. Keep `keystore/` and `android/assets/export.keystore` out of git — they are listed in `.gitignore`.

---

## 5. Build Distribution Server

To share the compiled APKs/AABs with others over your local network, you can start the lightweight Node.js distribution server:

1. Start the server:
   ```bash
   sh start_server.sh
   ```
2. Open `http://localhost:3000` in your web browser.
3. Anyone on the same network can access your IP address (e.g., `http://192.168.1.50:3000`) to view, browse, and download the builds directly onto their devices.

---

## 6. Standalone Collaboration Server

NotTiled supports real-time multi-user map editing (collaboration). To run a standalone collaboration server that can be exposed publicly via a Cloudflare Tunnel:

1. **Start the server:**
   ```bash
   sh start_collab_server.sh
   ```
   *This starts the server on port `5555` using pure TCP.*

2. **Expose with Cloudflare Tunnel (cloudflared):**
   ```bash
   cloudflared tunnel --url tcp://localhost:5555
   ```
   *(Or configure your tunnel config file to forward a public domain to `tcp://localhost:5555`)*


