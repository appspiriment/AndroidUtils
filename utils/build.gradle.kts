import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(appspirimentlibs.plugins.appspiriment.library)
    alias(appspirimentlibs.plugins.kotlin.android)
    alias(libs.plugins.vanniktech.publish)
}

appspiriment {
    namespace = "com.appspiriment.utils"
}

android {
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


//Running the ':utils:publishDevRelease' & ':utils:publishProdRelease' task will turn these
// to Dev Flavour and Prod flavour respectively. After running those tasks, close the build.gradle.kts
// and the sync. Verify the flavor and variant are correct ones. Then run publish Task (Local / Maven)
// This is since vanniktech publish library currently don't support publishing different flavour with
// differnt coordinates like artifact id.
var flavor = "prod"
var variant = "prodRelease"

mavenPublishing {
    coordinates(
        artifactId = "utils-$flavor",
        version = libs.versions.appspirimentUtils.get()
    )
    configure(
        AndroidSingleVariantLibrary(
            sourcesJar = true,
            publishJavadocJar = true,
            variant = variant,
        )
    )

    pom {
        name = "Appspiriment Utils"
        description =
            "A library with common util functions and extension methods to help kotlin developers easily use them."
        url =
            "https://github.com/appspiriment/AndroidConventionPlugins/tree/main/logging/logger"
    }
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    signAllPublications()
}

tasks.register("publishDevRelease"){
    buildFile.let{file->
        val lines = file.readLines().toMutableList()
        val idIndex = lines.indexOfFirst { it.contains("var flavor =" ) }
        lines.removeAt(idIndex)
        lines.add(idIndex, "var flavor = \"dev\"")

        val variantIndex = lines.indexOfFirst { it.contains("var variant =" ) }
        lines.removeAt(variantIndex)
        lines.add(variantIndex, "var variant = \"devRelease\"")

        file.writeText(lines.joinToString("\n"))
    }
}


tasks.register("publishProdRelease"){
    buildFile.let{file->
        val lines = file.readLines().toMutableList()
        val idIndex = lines.indexOfFirst { it.contains("var flavor =" ) }
        lines.removeAt(idIndex)
        lines.add(idIndex, "var flavor = \"prod\"")

        val variantIndex = lines.indexOfFirst { it.contains("var variant =" ) }
        lines.removeAt(variantIndex)
        lines.add(variantIndex, "var variant = \"prodRelease\"")

        file.writeText(lines.joinToString("\n"))
    }
}