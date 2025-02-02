plugins {
    alias(appspirimentlibs.plugins.appspiriment.compose.library)
    alias(libs.plugins.vanniktech.publish)
}
android {
    namespace = "com.appspiriment.composeutils"
}

mavenPublishing {
    coordinates(
        artifactId = "compose-utils",
        version = project.ext["libVersion"].toString()
    )

    pom {
        name = "Appspiriment Compose Utils"
        description = "A library with some simple compose views which is required for Appspiriment Developers, and some composable util functions."
        url = "https://github.com/appspiriment/AndroidUtils"
    }
    signAllPublications()
}