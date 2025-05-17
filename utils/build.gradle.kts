import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.SonatypeHost
plugins {
    alias(appspirimentlibs.plugins.google.android.library)
    alias(appspirimentlibs.plugins.kotlin.android)
    alias(libs.plugins.vanniktech.publish)
}

android {
    namespace = "com.appspiriment.utils"

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
}


dependencies {
    implementation(appspirimentlibs.bundles.android.base)
    testImplementation(appspirimentlibs.junit.test)

}

mavenPublishing {
    coordinates(
        artifactId = "utils",
        version = libs.versions.appspirimentUtils.get()
    )
    configure(
        AndroidSingleVariantLibrary(
            sourcesJar = true,
            publishJavadocJar = true,
            variant = "release",
        )
    )

    pom {
        name = "Appspiriment Utils"
        description =
            "A library with common util functions and extension methods to help kotlin developers easily use them."
        url = "https://github.com/appspiriment/AndroidUtils"
    }
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    signAllPublications()
}
