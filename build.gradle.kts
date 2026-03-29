// ╔════════════════════════════════════════════════════════════╗
// ║              Appspiriment Convention Plugins               ║
// ║                                                            ║
// ║  Current version: 0.0.14.dev-206                              ║
// ║                                                            ║
// ║  To check for updates or upgrade:                          ║
// ║  1. Visit: https://github.com/appspiriment/AndroidConventionPlugins/releases
// ║  2. Copy the latest version number (e.g. "0.1.0")          ║
// ║  3. Update/Uncomment the line inside plugins { } block:    ║
// ║ id("io.github.appspiriment.project") version "0.0.14.dev-206" ║
// ║                                                            ║
// ║  Or run: ./gradlew dependencyUpdates                       ║
// ║     (after adding com.github.ben-manes.versions plugin)    ║
// ║                                                            ║
// ║  Full changelog & documentation:                           ║
// ║  https://github.com/appspiriment/AndroidConventionPlugins  ║
// ╚════════════════════════════════════════════════════════════╝

plugins {
    // id("io.github.appspiriment.project") version "0.0.14.dev-206"

    alias(appspirimentlibs.plugins.appspiriment.library.hilt) apply false
    alias(appspirimentlibs.plugins.appspiriment.library.compose) apply false
    alias(appspirimentlibs.plugins.appspiriment.library.hilt.compose) apply false





    alias(appspirimentlibs.plugins.appspiriment.application) apply false
    alias(appspirimentlibs.plugins.appspiriment.library) apply false
    alias(appspirimentlibs.plugins.appspiriment.data) apply false


    alias(appspirimentlibs.plugins.google.android.application) apply false
    alias(appspirimentlibs.plugins.google.android.library) apply false
    alias(appspirimentlibs.plugins.kotlin.android) apply false
    alias(appspirimentlibs.plugins.kotlin.compose) apply false
    alias(appspirimentlibs.plugins.kotlin.jvm) apply false
    alias(appspirimentlibs.plugins.devtools.ksp) apply false
    alias(appspirimentlibs.plugins.dagger.hilt.android) apply false
    alias(appspirimentlibs.plugins.kotlinx.serialization) apply false
}
