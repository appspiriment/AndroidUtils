//    Current Appspiriment Plugin version: "0.0.5"
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.Properties
plugins {
//    id("io.github.appspiriment.project") version "0.0.5.+"
    alias(appspirimentlibs.plugins.google.android.application) apply false
    alias(appspirimentlibs.plugins.google.android.library) apply false
    alias(appspirimentlibs.plugins.kotlin.android) apply false
    alias(appspirimentlibs.plugins.kotlin.compose) apply false
    alias(appspirimentlibs.plugins.kotlin.jvm) apply false
    alias(appspirimentlibs.plugins.devtools.ksp) apply false
    alias(appspirimentlibs.plugins.dagger.hilt.android) apply false
    alias(appspirimentlibs.plugins.kotlinx.serialization) apply false
}
val libversionFile = "${project.rootDir.path}/libraryversion.properties"
subprojects {
    project.ext.set("libVersion", getLibraryDevVersion())
    tasks.register("updateVersionForPortal") {
        File(libversionFile).apply {
            val props = Properties()
            props.load(FileInputStream(this))
            props.getOrDefault("LASTDEV", 1).let {
                writeText(
                    "MAJOR=${libs.versions.appspirimentUtils.get()}\nLASTDEV=$it"
                )
            }
        }
    }
    tasks.register("updateVersionForLocal") {
        File(libversionFile).apply {
            val props = Properties()
            props.load(FileInputStream(this))
            props.getOrDefault("DEV", props.getOrDefault("LASTDEV", 1)).toString().toInt().inc()
                .let {
                    writeText("MAJOR=${libs.versions.appspirimentUtils.get()}\nDEV=${it}\nLASTDEV=$it")
                }
        }
    }
}
internal fun getLibraryDevVersion(): String {
    val propsFile = File(libversionFile)
    if (propsFile.exists().not()) {
        propsFile.writeText("MAJOR=${libs.versions.appspirimentUtils.get()}\nDEV=1\nLASTDEV=1")
    }
    if (propsFile.exists()) {
        val props = Properties()
        props.load(FileInputStream(propsFile))
        val major = props["MAJOR"].toString()
        val dev = if (true && props.containsKey("DEV")) {
            props["DEV"].toString().padStart(2, '0').let { ".dev-$it" }
        } else null
        return dev?.let { "$major$it" } ?: major
    } else {
        throw FileNotFoundException("Could not read libraryversion.properties!")
    }
}