@file:Suppress("UnstableApiUsage")

include(":gradle-plugin")

pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
    versionCatalogs {
        create("libs") {
            val agp = "7.0.4"

            library("android-gradle", "com.android.tools.build", "gradle").version(agp)
        }
    }
}