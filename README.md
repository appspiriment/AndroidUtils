# Appspiriment Android Utils

A collection of Android utility libraries for Jetpack Compose projects, published under `io.github.appspiriment`.

## Libraries

| Artifact | Latest Version | Status |
|---|---|---|
| `compose-utils` | ![compose-utils](https://img.shields.io/badge/compose--utils-0.1.0-brightgreen) | ✅ Updated |
| `utils` | ![utils](https://img.shields.io/badge/utils-0.0.5.dev--11-blue) | Stable |
| `logutils-dev` / `logutils-prod` | ![logutils](https://img.shields.io/badge/logutils-0.0.1-blue) | Stable |
| `update-utils` | ![update-utils](https://img.shields.io/badge/update--utils-0.0.1-blue) | Stable |

---

## Installation

Add `mavenCentral()` to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
```

---

## compose-utils `0.1.0` ✅

Compose components, a theme system, `UiText` / `UiColor` / `UiImage` / `UiDimen` wrappers,
navigation animations, ViewModel base classes, and more.

```toml
# libs.versions.toml
[versions]
composeUtils = "0.1.0"

[libraries]
appspiriment-compose = { group = "io.github.appspiriment", name = "compose-utils", version.ref = "composeUtils" }
```

```kotlin
// build.gradle.kts
dependencies {
    implementation(libs.appspiriment.compose)
}
```

### What's new in 0.1.0

#### Theme system
- **`AppFontFamily` sealed class** — `Roboto`, `Noto(fontPadding)`, `System`, `Custom`, `GmsFont`; replaces the old lambda-based font API
- **Non-composable theme factories** — `baseColors(context)`, `createSizes(context)`, `createBaseTypography(context, font)` are now plain functions, enabling correct `remember {}` memoization in `CompositionBaseProvider`
- **Fixed `isNotoFont`** — was always `false` due to a lambda equality bug; now correctly checks `fontFamily is AppFontFamily.Noto`
- **Fixed `notoFontPadding`** — was always `0.dp`; now reads `Noto.fontPadding` (default `4.dp`)
- **Fixed `textSizeResource()`** — `dimensionResource().value.sp` was double-applying text scaling; now uses `getDimension() / scaledDensity`
- **Fixed `Dimens.kt` copy-paste bugs** — `paddingTiny` and `cornerRadiusXXXLarge` were both reading wrong resource IDs
- **M3 type-scale aliases** — 15 extension properties on `BaseTextStyles` (`labelSmall` → `displayLarge`) for Material 3 interop

#### Navigation animations
- **`NavTransition` data class** — bundles all four lambdas (`enter`, `exit`, `popEnter`, `popExit`) into one object; pass directly to `NavHost` or `animatedComposable`
- **`NavTransitions` preset factory** — six ready-made transitions:

  | Preset | Use case |
  |---|---|
  | `slideFromRight()` | Standard forward push (default) |
  | `slideFromLeft()` | RTL / reverse-direction push |
  | `slideFromBottom()` | Full-screen modal rising from bottom |
  | `slideFromTop()` | Top tray, notification detail |
  | `fade()` | Tab switches, peer-level screens |
  | `scaleAndFade()` | Settings overlay, dialog-like |
  | `none()` | Instant switch (splash → home) |

- **`animatedComposable<T>(transition)`** — fixed; old implementation used `AnimatedVisibility` inside `composable<T>` causing a double-animation layer and an exit that never played
- **Fixed `SlideInRightToLeft.exit`** — was `+x` (wrong direction); now correctly `-x`
- **Correct `popEnter` / `popExit`** — were identical to `enter`/`exit`; now distinct for proper back-navigation feel

  ```kotlin
  // Before (broken)
  NavHost { animatedComposable<HomeRoute> { HomeScreen() } }

  // After
  NavHost(
      enterTransition    = defaultEnterTransition,
      exitTransition     = defaultExitTransition,
      popEnterTransition = defaultPopEnterTransition,
      popExitTransition  = defaultPopExitTransition,
  ) {
      animatedComposable<HomeRoute>    { HomeScreen() }
      animatedComposable<DetailRoute>(NavTransitions.slideFromBottom()) { DetailScreen() }
      animatedComposable<SettingsRoute>(NavTransitions.scaleAndFade())  { SettingsScreen() }
  }
  ```

#### Bug fixes
| Location | Bug | Fix |
|---|---|---|
| `Toast.kt` | `Toast.show()` called directly in composition — fires on every recomposition | Wrapped in `LaunchedEffect` |
| `UiDimen.kt` | `getDimension(resId).dp` — `getDimension()` returns **px**, not dp; values were `density×` too large on HDPI | Divide by `displayMetrics.density` |
| `AppsImageText.kt` | `modifier.apply { clickable {} }` — `apply` does not chain `Modifier`; click silently dropped | Changed to `modifier.then(Modifier.clickable {})` |
| `ActionFinder.kt` | `offsetSoFar += actionEndX` — accumulated end position instead of width; wrong swipe boundaries for all actions after the first | Changed to `+= actionWidth` |
| `ComposeFlowUtils.kt` | `lifecycleScope.launch` inside `LaunchedEffect` — inner coroutine outlives composition (leak) | Removed inner `launch`; collect directly in `LaunchedEffect` |
| `UiText.isBlank()` / `isEmpty()` | Threw `Exception` for resource-backed types | Returns `false` (safe no-Context default) |

#### API & ergonomics
- **`AppsText`** — canonical name for the text composable; `AppspirimentText` soft-deprecated with `ReplaceWith`
- **Item-based `AppsDropdown` overload** — `AppsDropdown(options, selectedItem: T?, onItemSelected: (T) -> Unit)` — no more manual index tracking
- **`AppsTextField` deprecation improved** — now includes `ReplaceWith(AppsValidatedTextField(state))` and `DeprecationLevel.WARNING`
- **`screenWidthFractionPx` privatised** — was public PascalCase `GetScreenWidthPercentageInPx`; renamed and made private
- **`DrawerItem.from()` unused `<T>` generic removed**
- **`animationDurationMs = 4_00` → `400`** — misleading numeric literal fixed

#### SOLID / access-modifier fixes
- `UiEventsViewModel.onEvent` `internal` → `abstract fun` — `internal abstract` in a published library breaks the override contract for consumers in other modules
- `UiStateEventsViewModel.sendUiEvent` / `updateUiState` → `protected` — prevents external callers from bypassing the event channel
- `UiStateEventsAndroidViewModel.sendUiEvent` → `protected` — matches `UiStateEventsViewModel` pattern

#### Cleanup
- Deleted `Lottie.kt` — entire file was commented-out dead code
- Deleted empty `values-xhdpi/dimen.xml` and `values-xxhdpi/dimen.xml` (vestigial View-era files)
- Removed dead `bi_brand` color resource
- Removed unused `LocalContext.current` imports in button components

---

### Component Reference

#### Theme
| API | Purpose |
|---|---|
| `CompositionBaseProvider` | Root theme provider |
| `MalayalamCompositionBaseProvider` | Convenience wrapper defaulting to Noto font |
| `Appspiriment.colors` | `BaseColors` — semantic color tokens |
| `Appspiriment.sizes` | `Sizes` — spacing, icon, corner-radius design tokens |
| `Appspiriment.typography` | `BaseTextStyles` + M3 aliases |
| `AppFontFamily` | `Roboto` · `Noto` · `System` · `Custom` · `GmsFont` |

#### Containers
| Component | Description |
|---|---|
| `AppsPageScaffold` / `PageScaffold` | Scaffold with top bar, loader slot |
| `AppsDrawerScaffold` | Navigation drawer scaffold |
| `AppsTopBar` | Top app bar with image title, back, action buttons |
| `AppsBottomNavigation` | NavController-aware bottom navigation bar |
| `AppsBottomNavigationNavHost` | Scaffold + NavHost + bottom bar |
| `SwipeableActionsBox` | Swipe-to-reveal action container |
| `TitledCardView` | Card with floating title |
| `SmartPullToRefreshBox` | Pull-to-refresh wrapper |
| `AppsBottomSheet` | Modal bottom sheet |

#### Core
| Component | Description |
|---|---|
| `AppsText` | Primary text composable |
| `AppsImageText` | Text with leading / trailing icon |
| `AppsImage` | Unified image composable (`UiImage`-backed) |
| `AppsIcon` | Icon composable |
| `AppsButton` / `AppsIconButton` / `AppsImageButton` / `CircularButton` | Button variants |
| `AppsDropdown` | Material 3 dropdown — generic, `UiText`, and item-based overloads |
| `AppsValidatedTextField` | Stateful text field with `ValidatedTextFieldState` |
| `FullscreenLoader` | Blocking loading overlay |
| `MessageDialog` | Configurable alert dialog |
| `Modifier.shimmerEffect()` | Skeleton loading shimmer |
| `Modifier.circleBackground` | Circle background modifier |

#### Wrappers
| Class | Variants |
|---|---|
| `UiText` | `DynamicString` · `StringResource` · `PluralResource` · `StringArrayResource` · `DynamicAnnotatedString` |
| `UiColor` | `DynamicColor` · `ColorResource` · `HexColor` |
| `UiImage` | Vector · Drawable · URL · Painter |
| `UiDimen` | `DynamicDp` · `DynamicTextUnit` · `DimenResource` |
| `SerializedColor` | `@JvmInline` + `KSerializer` for persisting `Color` |

#### ViewModel base classes
| Class | Purpose |
|---|---|
| `UiStateEventsViewModel<S, E, U>` | State flow + UI event channel |
| `UiEventsViewModel<E, U>` | UI event channel only (stateless) |
| `UiStateEventsAndroidViewModel<S, E, U>` | `AndroidViewModel` variant |

#### Utilities
| Utility | Description |
|---|---|
| `rememberPermissionRequest(…)` | Dialog-driven permission flow |
| `PermissionHandler` | Full-screen permission gate |
| `rememberPhotoPicker` | Photo picker + crop |
| `Flow<T>.observeWithLifecycle(…)` | Lifecycle-aware flow collection |
| `DisableSoftKeyboard` | Suppresses soft keyboard |
| `genericNavType<T>()` | Serializable nav type factory |

---

## Other Libraries

### utils `0.0.5`
Core Kotlin/Android extension functions and common utilities.

```toml
[libraries]
appspiriment-utils = { group = "io.github.appspiriment", name = "utils", version = "0.0.5.dev-11" }
```

### logutils `0.0.1`
Verbose `dev` and silent `prod` logging flavours.

```kotlin
dependencies {
    debugImplementation("io.github.appspiriment:logutils-dev:0.0.1")
    releaseImplementation("io.github.appspiriment:logutils-prod:0.0.1")
}
```

### update-utils `0.0.1`
Firebase Remote Config–driven app update flow with Compose UI.

```kotlin
dependencies {
    implementation("io.github.appspiriment:update-utils:0.0.1")
}
```

---

## License

[Apache License 2.0](LICENSE) · [GitHub](https://github.com/appspiriment/AndroidUtils)
