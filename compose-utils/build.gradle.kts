import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(appspirimentlibs.plugins.google.android.library)
    alias(appspirimentlibs.plugins.kotlin.android)
    alias(appspirimentlibs.plugins.kotlin.compose)
    alias(libs.plugins.vanniktech.publish)
}
android {
    namespace = "com.appspiriment.composeutils"
    compileSdk = appspirimentlibs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = appspirimentlibs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        appspirimentlibs.versions.javaVersion.get().toInt().let{
            JavaVersion.toVersion(it)
        }.let{
            sourceCompatibility = it
            targetCompatibility = it
        }
    }
    kotlinOptions {
        jvmTarget = appspirimentlibs.versions.javaVersion.get()
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(appspirimentlibs.androidx.compose.bom))
    implementation(appspirimentlibs.bundles.android.compose)
    debugImplementation(appspirimentlibs.androidx.ui.tooling)
    debugImplementation(appspirimentlibs.androidx.ui.test.manifest)
    testImplementation(appspirimentlibs.junit.test)

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