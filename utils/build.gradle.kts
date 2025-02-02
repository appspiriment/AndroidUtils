import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(appspirimentlibs.plugins.google.android.library)
    alias(appspirimentlibs.plugins.kotlin.android)
//    alias(libs.plugins.vanniktech.publish)
    `maven-publish`
    signing
}

android {
    namespace = "com.appspiriment.utils"
    compileSdk = appspirimentlibs.versions.compileSdk.get().toInt()
    val javaVersion = appspirimentlibs.versions.javaVersion.get().toInt().let{
        JavaVersion.toVersion(it)
    }

    defaultConfig{
        appspirimentlibs.versions.minSdk.get().toInt().let {
            aarMetadata {
                minCompileSdk = it
            }
            minSdk = it
        }
    }
    packaging {
        resources {
            resources.excludes.add("META-INF/*")
        }
    }

    kotlinOptions{
            jvmTarget = javaVersion.toString()
    }
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        isCoreLibraryDesugaringEnabled = false
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

    dependencies {
        implementation(appspirimentlibs.bundles.android.base)
        testImplementation(appspirimentlibs.bundles.android.test)
    }
}

publishing {
    publications {
        afterEvaluate {
            android.libraryVariants.filter { it.buildType.name == "release" }.forEach { variant ->
                // Only consider release
                publishing.publications.create<MavenPublication>(variant.name ) {
                    from(components.findByName(variant.name))
                    groupId = "io.github.appspiriment"
                    artifactId = "utils-${variant.flavorName}"
                    version = project.ext["libVersion"].toString()
                }
            }
        }
    }

    // Repository configuration
    repositories {
        maven {
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")

            credentials {
                username = project.findProperty("ossrhUsername") as String
                password = (project.findProperty("ossrhPassword") as String).also{println(it)}
            }
        }
    }
}


signing {
    android.libraryVariants.filter { it.buildType.name == "release" }.forEach { variant ->
        sign(publishing.publications[variant.name])
    }
}
