pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositories {
        maven { setUrl("https://jitpack.io") }
    }
}


rootProject.name = "OPSC PART 3"
include(":app")
 