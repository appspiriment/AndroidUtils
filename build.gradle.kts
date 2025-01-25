// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
//    id("io.github.appspiriment.project") version "0.0.3.dev-29"
    alias(appspirimentlibs.plugins.google.android.application) apply false
    alias(appspirimentlibs.plugins.google.android.library) apply false
    alias(appspirimentlibs.plugins.kotlin.android) apply false
    alias(appspirimentlibs.plugins.kotlin.compose) apply false
    alias(appspirimentlibs.plugins.kotlin.jvm) apply false
    alias(appspirimentlibs.plugins.devtools.ksp) apply false
    alias(appspirimentlibs.plugins.dagger.hilt.android) apply false
    alias(appspirimentlibs.plugins.kotlinx.serialization) apply false
}