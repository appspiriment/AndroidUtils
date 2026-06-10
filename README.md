# Appspiriment Android Utils

A collection of Android utility libraries for Jetpack Compose projects, published under `io.github.appspiriment`.

## Libraries

| Artifact | Version | Description |
|---|---|---|
| `compose-utils` | [![compose-utils](https://img.shields.io/badge/compose--utils-0.1.0-blue)](https://github.com/appspiriment/AndroidUtils) | Compose UI components, theme system, wrappers |
| `utils` | [![utils](https://img.shields.io/badge/utils-0.1.0-blue)](https://github.com/appspiriment/AndroidUtils) | Kotlin extension functions and Android utilities |
| `logutils-dev` / `logutils-prod` | [![logutils](https://img.shields.io/badge/logutils-0.1.0-blue)](https://github.com/appspiriment/AndroidUtils) | Logging utilities with dev/prod flavours |
| `update-utils` | [![update-utils](https://img.shields.io/badge/update--utils-0.1.0-blue)](https://github.com/appspiriment/AndroidUtils) | Firebase Remote Config–driven app update flows |

---

## Installation

Add `mavenCentral()` (or `mavenLocal()` for local builds) to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        // mavenLocal() // for local snapshot builds
    }
}
```

### compose-utils

Compose components, a theme system, `UiText`/`UiColor`/`UiImage`/`UiDimen` wrappers, navigation animations, ViewModel base classes, and more.

```kotlin
// settings.gradle.kts / libs.versions.toml
[versions]
appspirimentComposeUtils = "0.1.0"

[libraries]
appspiriment-compose = { group = "io.github.appspiriment", name = "compose-utils", version.ref = "appspirimentComposeUtils" }
```

```kotlin
// build.gradle.kts
dependencies {
    implementation(libs.appspiriment.compose)
}
```

### utils

Core Kotlin/Android extension functions, serialization helpers, and common utilities.

```kotlin
[versions]
appspirimentUtils = "0.1.0"

[libraries]
appspiriment-utils = { group = "io.github.appspiriment", name = "utils", version.ref = "appspirimentUtils" }
```

### logutils

Logging utilities with separate `dev` (verbose) and `prod` (silent/crash-only) flavour artifacts.

```kotlin
[versions]
appspirimentLogUtils = "0.1.0"

[libraries]
appspiriment-logutils-dev  = { group = "io.github.appspiriment", name = "logutils-dev",  version.ref = "appspirimentLogUtils" }
appspiriment-logutils-prod = { group = "io.github.appspiriment", name = "logutils-prod", version.ref = "appspirimentLogUtils" }
```

```kotlin
// build.gradle.kts — pick one per build variant
dependencies {
    debugImplementation(libs.appspiriment.logutils.dev)
    releaseImplementation(libs.appspiriment.logutils.prod)
}
```

### update-utils

Composable update-gate UI powered by Firebase Remote Config — handles immediate and flexible update flows.

```kotlin
[versions]
appspirimentUpdateUtils = "0.1.0"

[libraries]
appspiriment-update = { group = "io.github.appspiriment", name = "update-utils", version.ref = "appspirimentUpdateUtils" }
```

---

## compose-utils — Component Reference

### Theme
| Class / Object | Purpose |
|---|---|
| `CompositionBaseProvider` | Root theme provider — wrap your Activity content here |
| `MalayalamCompositionBaseProvider` | Convenience entry-point defaulting to Noto font |
| `Appspiriment.colors` | `BaseColors` — semantic color tokens |
| `Appspiriment.sizes` | `Sizes` — spacing, icon, corner-radius design tokens |
| `Appspiriment.typography` | `BaseTextStyles` + M3 semantic aliases |
| `Appspiriment.flags` | `BaseFlags` — `isNotoFont`, `notoFontPadding` |
| `AppFontFamily` | Sealed class — `Roboto`, `Noto`, `System`, `Custom`, `GmsFont` |

### Navigation Animations
| API | Use case |
|---|---|
| `NavTransition` | Data class holding all four transition lambdas |
| `NavTransitions.slideFromRight()` | Standard forward push (default) |
| `NavTransitions.slideFromLeft()` | RTL / reverse push |
| `NavTransitions.slideFromBottom()` | Full-screen modal |
| `NavTransitions.slideFromTop()` | Top tray / notification detail |
| `NavTransitions.fade()` | Tab switch / peer screens |
| `NavTransitions.scaleAndFade()` | Settings overlay / dialog-like |
| `NavTransitions.none()` | Instant switch (splash → home) |
| `animatedComposable<T>(transition)` | `NavGraphBuilder` extension — replaces `composable<T>` |
| `defaultEnterTransition` etc. | Top-level vals for `NavHost` global defaults |

### Containers
| Component | Description |
|---|---|
| `AppsPageScaffold` / `PageScaffold` | Scaffold with top bar, bottom bar, and fullscreen loader slot |
| `AppsDrawerScaffold` | Navigation drawer scaffold |
| `AppsTopBar` | Opinionated top app bar supporting image titles, back, and action buttons |
| `AppsBottomNavigation` | Bottom navigation bar (NavController-aware) |
| `AppsBottomNavigationNavHost` | Scaffold + NavHost + bottom bar integrated |
| `SwipeableActionsBox` | Swipe-to-reveal action container |
| `TitledCardView` | Card with floating title header |
| `SmartPullToRefreshBox` | Pull-to-refresh wrapper |
| `AppsBottomSheet` | Modal bottom sheet with optional title/close |

### Core Components
| Component | Description |
|---|---|
| `AppsText` | Primary text composable (replaces `AppspirimentText`) |
| `AppsImageText` | Text with leading/trailing icon |
| `AppsImage` | Unified image composable (`UiImage`-backed) |
| `AppsIcon` | Icon composable (`ImageVector` or `Painter`) |
| `AppsButton` | Standard button |
| `AppsIconButton` | Icon-only button with `UiImage` |
| `AppsImageButton` | Button with text + icon |
| `CircularButton` | Round floating-action-style button |
| `AppsDropdown` | Material 3 animated dropdown (generic + `UiText` overloads) |
| `AppsValidatedTextField` | Stateful text field with `ValidatedTextFieldState` |
| `AppsSelectableText` | Toggling chip / selectable text |
| `FullscreenLoader` | Blocking loading overlay |
| `MessageDialog` | Configurable alert dialog |
| `VerticalSpacer` / `HorizontalSpacer` | Typed spacers |
| `Modifier.shimmerEffect()` | Skeleton loading shimmer modifier |
| `Modifier.circleBackground` | Circle background modifier |

### Wrappers
| Class | Description |
|---|---|
| `UiText` | Sealed class — `DynamicString`, `StringResource`, `PluralResource`, `AnnotatedString` |
| `UiColor` | Sealed class — `DynamicColor`, `ColorResource`, `HexColor` |
| `UiImage` | Sealed class — vector, drawable, URL, painter |
| `UiDimen` | Sealed class — `DynamicDp`, `DynamicTextUnit`, `DimenResource` |
| `SerializedColor` | `@JvmInline` value class with `KSerializer` for persisting `Color` |

### ViewModel Base Classes
| Class | Generics | Purpose |
|---|---|---|
| `UiStateEventsViewModel<S, E, U>` | State, Event, UiEvent | State + event channel |
| `UiEventsViewModel<E, U>` | Event, UiEvent | Event channel only (stateless) |
| `UiStateEventsAndroidViewModel<S, E, U>` | State, Event, UiEvent | `AndroidViewModel` variant |

### Utilities
| Utility | Description |
|---|---|
| `rememberPermissionRequest(…)` | Dialog-driven permission flow |
| `PermissionHandler` | Full-screen permission gate composable |
| `rememberPhotoPicker` | Photo picker + crop integration |
| `rememberSpeechToText` | Speech-to-text launcher |
| `Flow<T>.observeWithLifecycle(…)` | Lifecycle-aware flow collector |
| `DisableSoftKeyboard` | Composable that suppresses the soft keyboard |
| `genericNavType<T>()` | Parcelable/Serializable nav type factory |
| `EventStabilizers` | `stabilize()` / `stabilizeLambda()` for stable callbacks |

---

## Version History

| Version | Highlights |
|---|---|
| **0.1.0** | Theme system rewrite (`AppFontFamily`, non-composable factories, M3 aliases); `NavTransition` + 6 preset animations; `AppsText` canonical name; item-based `AppsDropdown` overload; critical bug fixes (Toast recomposition, `UiDimen` px/dp, `AppsImageText` click, `ActionFinder` offset, flow coroutine leak) |
| 0.0.6 | Dropdown improvements, various component updates |
| 0.0.5 | Initial public release |

---

## License

This project is licensed under the [Apache License 2.0](LICENSE).

For more details, visit the [GitHub Repository](https://github.com/appspiriment/AndroidUtils).
