# MCWrapper - Android Minecraft Server

A mobile Android application that runs a Minecraft server using native C++ code via JNI.

## Requirements

- Android SDK 34+
- Android NDK (r25+)
- Java 11+
- Gradle 8.0+

## Building

### Setup

1. Install Android Studio
2. Install Android SDK (API 34)
3. Install Android NDK (latest)
4. Set `ANDROID_HOME` and `ANDROID_NDK_HOME` environment variables

### Building APK

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK (optimized)
./gradlew assembleRelease
```

The APK will be generated in `app/build/outputs/apk/`

## Features

- Native C++ Minecraft server running on Android
- Simple UI with Start/Stop controls
- Runs as a foreground service
- Automatic permissions handling
- Low-power background operation

## Installation

1. Build the APK (see Building section)
2. Transfer APK to your Android device
3. Enable "Unknown Sources" in Settings > Security
4. Install the APK
5. Grant requested permissions
6. Run the app and start the server

## Troubleshooting

- **Server won't start**: Check logs in Android Studio Logcat (filter by "MCWrapper")
- **Permission errors**: Ensure app has storage permissions granted
- **Low memory errors**: Close other apps before running server
- **Network issues**: Ensure device has internet access

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/mcwrapper/
│   │   ├── MainActivity.java      # UI and controls
│   │   └── ServerService.java     # Background service
│   ├── cpp/
│   │   ├── native-lib.cpp         # JNI bridge
│   │   └── CMakeLists.txt         # Build configuration
│   ├── res/
│   │   ├── layout/                # UI layouts
│   │   ├── values/                # Resources
│   │   └── xml/                   # Configuration
│   └── AndroidManifest.xml        # App manifest
└── build.gradle                   # Module build config
```

## License

MIT
