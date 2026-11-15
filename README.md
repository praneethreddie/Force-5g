# Aero 5G Force - Modern Network Radio Controller

A sleek, 100% ad-free Android utility to switch cellular network profiles (e.g., `NR only`, `NR/LTE`) to maximize unlimited 5G data speeds.

---

## Key Features

1. **Cyber Dark Telemetry Dashboard:** Clean, high-tech interface with live carrier and network detection.
2. **Multi-Stage Fallback Launch Engine:** Handles generic Android (`RadioInfo`), modern Samsung One UI (`ServiceModeApp`), and testing intent variations.
3. **Android Quick Settings Pull-Down Tile:** Switch modes straight from the notification shade without searching for the app icon.
4. **VoNR Voice Call Advisory:** Guides users on the difference between `NR only` (pure 5G SA) and `NR/LTE` (5G speed + 4G VoLTE calls) so they never miss incoming calls.
5. **Zero Ads, Zero Trackers:** Built purely for utility and performance.

---

## Project Structure

```
force5g/
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/signalforce/fiveg/
│       │   ├── MainActivity.kt
│       │   ├── NetworkHelper.kt
│       │   └── RadioSwitchTileService.kt
│       └── res/
│           ├── drawable/
│           ├── layout/activity_main.xml
│           ├── values/
│           └── xml/
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/gradle-wrapper.properties
├── build.gradle.kts
├── settings.gradle.kts
└── gradlew.bat
```

---

## How to Build the APK & Play Store Bundle

### Option A: Using Android Studio (Recommended)
1. Open **Android Studio**.
2. Click **Open** and select the folder:
   `C:\Users\USER\.gemini\antigravity\scratch\force5g`
3. Wait for Gradle to sync dependencies.
4. **To test on your phone (Debug APK):**
   * Go to **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)**.
   * Android Studio will compile it and pop up a notification with a **"locate"** link directly to `app-debug.apk`.
   * Transfer this `.apk` to your phone or run directly on your connected Samsung device.
5. **To prepare for Google Play Store (Release AAB):**
   * Go to **Build** → **Generate Signed Bundle / APK...**
   * Select **Android App Bundle (.aab)**.
   * Create or choose your keystore, and click **Release**.
   * Upload the generated `.aab` file to your Google Play Console dashboard.

### Option B: Using Command Line (Terminal)
```powershell
cd "C:\Users\USER\.gemini\antigravity\scratch\force5g"

# Build Debug APK
./gradlew assembleDebug

# Build Play Store Release Bundle
./gradlew bundleRelease
```
The compiled APK will be output to:  
`app/build/outputs/apk/debug/app-debug.apk`
