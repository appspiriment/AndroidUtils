
plugins {
    alias(appspirimentlibs.plugins.appspiriment.library)
    alias(libs.plugins.vanniktech.publish)
}

android{
    namespace = "com.appspiriment.composeutils"
}

mavenPublishing {
    coordinates(
        artifactId = "compose-utils",
        version = libs.versions.composeUtils.get()
    )

    pom {
        name = "Appspiriment Compose Utils"
        description = "A library with some simple compose views which is required for Appspiriment Developers, and some composable util functions."
        url = "https://github.com/appspiriment/AndroidUtils"
    }
    signAllPublications()
}