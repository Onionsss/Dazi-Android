include(":core")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Dazi"
include(":app")
include(":user")
include(":user:login")
include(":user:login-protocol")
include(":user:login-sample")
include(":router")
include(":resource")
include(":domain")
include(":domain:center")
include(":domain:center-protocol")
include(":core:redux")
include(":core:platform-hub")
include(":core:platform-hub-navigation")
include(":core:constants")
include(":core:common")
include(":feature")
include(":feature:home")
include(":feature:my")
include(":feature:community")
include(":core:ext")
