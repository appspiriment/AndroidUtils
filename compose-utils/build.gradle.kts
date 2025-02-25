import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(appspirimentlibs.plugins.appspiriment.library.base)
    alias(appspirimentlibs.plugins.kotlin.compose)
    alias(libs.plugins.vanniktech.publish)
}
android {
    namespace = "com.appspiriment.composeutils"
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(appspirimentlibs.androidx.compose.bom))
    implementation(appspirimentlibs.bundles.android.compose)
    debugImplementation(appspirimentlibs.androidx.ui.tooling)
    debugImplementation(appspirimentlibs.androidx.ui.test.manifest)
}

mavenPublishing {
    coordinates(
        artifactId = "compose-utils",
        version = libs.versions.composeUtils.get()
    )

    pom {
        name = "Appspiriment Compose Utils"
        description = "A library with some simple compose components and utilit functions which helps in Android development."
        url = "https://github.com/appspiriment/AndroidUtils"
    }
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)

    signAllPublications()
}