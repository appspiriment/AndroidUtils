import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.SonatypeHost
plugins {
    alias(appspirimentlibs.plugins.appspiriment.library.base)
    alias(libs.plugins.vanniktech.publish)
}

android {
    namespace = "com.appspiriment.logutils"

    defaultConfig{
        aarMetadata {
            minCompileSdk = appspirimentlibs.versions.minSdk.get().toInt()
        }
    }

    appspiriment {
        enableUtils = false
        enableMinify = false
    }

    productFlavors {
        setFlavorDimensions(listOf("mode"))
        create("dev") {
            dimension = "mode"
        }
        create("prod") {
            dimension = "mode"
        }
    }
    sourceSets {
        getByName("dev"){
            res.srcDirs("src/dev/res")
            java.srcDirs("src/dev/java")
        }
        getByName("prod"){
            res.srcDirs("src/prod/res")
            java.srcDirs("src/prod/java")
        }
    }
}

//val flavour = "dev"
val flavour = "prod"
mavenPublishing {
    coordinates(
        artifactId = "logutils-$flavour",
        version = libs.versions.logUtils.get()
    )
    configure(
        AndroidSingleVariantLibrary(
            sourcesJar = true,
            publishJavadocJar = true,
            variant = "${flavour}Release",
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
