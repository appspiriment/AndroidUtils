import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.SonatypeHost
plugins {
    alias(appspirimentlibs.plugins.appspiriment.library.base)
    alias(libs.plugins.vanniktech.publish)
}

android {
    namespace = "com.appspiriment.utils"

    defaultConfig{
        aarMetadata {
            minCompileSdk = appspirimentlibs.versions.minSdk.get().toInt()
        }
    }

    appspiriment {
        enableUtils = false
        enableMinify = false
    }
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
