# Approach

Approach is a local-first Kotlin Multiplatform app for discovering and connecting with nearby people
without requiring an internet connection.

The app targets Android and iOS using shared Kotlin, Compose Multiplatform, and a shared local
database layer.


## Goals

- Discover nearby people using local device capabilities.
- Keep profile data on the user's device.
- Avoid requiring an internet connection for core app behavior.
- Share UI, domain, state, and local data code across Android and iOS.
- Keep platform-specific nearby technology behind small shared interfaces.


## Architecture

Approach uses Kotlin Multiplatform with three main modules:

`androidApp`: The Android application shell. It owns the Android manifest, app icon, launch activity, permissions, and Android packaging.

`iosApp`: The iOS application shell. It owns the Swift app entry point, iOS project settings, app icons, signing, and platform privacy declarations.

`shared`: It contains the main application code shared across platforms.

### Shared Module

The shared module is organized around Kotlin Multiplatform source sets:

`shared/src/commonMain` contains platform-neutral code like Compose UI, ViewModels, UI state, domain models, database and repositories.

`shared/src/androidMain` contains Android-specific code like Android Compose host bridge, BLE, Wi-Fi and nearby-device implementations, Android permission handling and foreground/background services.

`shared/src/iosMain` contains iOS-specific code like Compose UIViewController bridge, CoreBluetooth and MultipeerConnectivity implementations and iOS permission/status handling.


## Testing

The project uses GitHub Actions to validate Android and iOS builds on every pull request.

Current CI checks:
- Android debug app build
- iOS simulator framework link

Testing confidence is tracked in layers:
```text
Build-verified      CI compiles and links targets
Logic-verified      unit tests cover shared behavior
UI-verified         emulator/simulator/manual UI checks
Device-verified     real Bluetooth/Wi-Fi behavior tested on hardware
```


## Development

All changes should go through feature branches and pull requests.
```bash
git checkout main
git pull
git checkout -b feature/example-change
```

Commit messages should be short and descriptive:
```text
feat: add shared profile model
fix: handle missing nearby permission
build: update Compose Multiplatform
docs: document local-only architecture
test: add profile repository tests
```

Pull requests should pass CI before merging into `main`.
