import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(appspirimentlibs.plugins.appspiriment.library.base)
    alias(appspirimentlibs.plugins.kotlin.compose)
    alias(libs.plugins.vanniktech.publish)
}
android {
    namespace = "com.appspiriment.updateutils"
}
appspiriment {
    enableMinify = false
}
dependencies {
    implementation(platform(appspirimentlibs.androidx.compose.bom))
    implementation(appspirimentlibs.bundles.android.compose)
    implementation(appspirimentlibs.appspiriment.compose)

    debugImplementation(appspirimentlibs.androidx.ui.tooling)
    debugImplementation(appspirimentlibs.androidx.ui.test.manifest)

    implementation(platform(appspirimentlibs.firebase.bom))
    implementation(appspirimentlibs.firebase.config.ktx)
}

mavenPublishing {
    coordinates(
        artifactId = "update-utils",
        version = libs.versions.updateUtils.get()
    )

    pom {
        name = "Appspiriment Update Utils"
        description = "A lightweight Android library that simplifies the process of checking for and handling app updates using Firebase Remote Config. Provides composable UI components for immediate and flexible update flows, allowing you to easily integrate update checks into your Jetpack Compose applications."
        url = "https://github.com/appspiriment/AndroidUtils"
    }
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    signAllPublications()
}