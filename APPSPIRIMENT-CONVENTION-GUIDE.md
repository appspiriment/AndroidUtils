# Appspiriment Convention Plugin Guide

**Plugin version**: 0.0.14.dev-203
**Last auto-updated**: 2026-03-07

This project uses the Appspiriment convention plugins to standardize Android project setup, dependencies, and architecture.

## 1. Core Automated Features

- **SDK Standards**: compileSdk 36, targetSdk 36, minSdk 26.
- **Modern Java**: Java 21 toolchain with Kotlin 2.1+ integration.
- **DI & Processing**: Hilt and KSP integration pre-configured for App and Library modules.
- **Centralized Versions**: Shared catalog via `appspirimentlibs`.
- **Theme System**: Includes `io.github.appspiriment:default-theme` for semantic UI resources.

## 2. Per-Module Configuration

Customize your module behavior using the `appspiriment` block. Note: We use the Gradle Property API, so use `.set()` for configuration.

```kotlin
appspiriment {
    enableHilt.set(true)      // Enabled by default for app modules
    enableCompose.set(false)   // Explicitly toggle UI features
    enableMinify.set(true)    // Enable R8 shrinking
    enableUtils.set(true)     // Shared helper extensions
}
```

## 3. The Data Layer Plugin

When using `id("io.github.appspiriment.data")`, you gain access to a powerful, nested configuration block for enterprise data requirements:

```kotlin
dataLayer {
    // Persistence
    room {
        enabled.set(true)
        usePaging.set(true)
    }
    
    // Networking
    retrofit {
        enabled.set(true)
        useChucker.set(true)             // Network inspector (Debug only)
        useKotlinSerialization.set(true) // Configures JSON conversion
    }
    
    // Security & Storage
    security {
        enabled.set(true)   // Adds Tink and Encrypted Preferences
    }
    dataStore {
        enabled.set(true)   // Adds Jetpack DataStore
    }
    
    // Background Tasks
    workManager {
        enabled.set(true)   // Adds WorkManager + Hilt-Work integration
    }
}
```

## 4. Overriding Resources

To customize the theme, override the resource names in your local `src/main/res` folder.

**Examples:**
- Primary Color: `<color name="md_sys_color_primary">#your_hex</color>`
- Standard Spacing: `<dimen name="padding_medium">16dp</dimen>`

See default names: [Theme Resources Repository](https://github.com/appspiriment/AndroidConventionPlugins/tree/main/default-theme/src/main/res)

## 5. Maintenance & Updates

Current version: `id("io.github.appspiriment.project") version "0.0.14.dev-203"`

To update:
1. Check [Releases](https://github.com/appspiriment/AndroidConventionPlugins/releases)
2. Update the version number in your root `build.gradle.kts`.
3. Sync Gradle.

---
**Support**
- [Issues & Feature Requests](https://github.com/appspiriment/AndroidConventionPlugins/issues)

Happy building! 🚀