pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Nytimes"
include(":app")
include(":core")
include(":data")
include(":domain")
include(":core:common")
include(":core:android")
include(":core:ui")
include(":core:local")
include(":core:remote")
include(":domain:auth")
include(":domain:news")
include(":domain:weather")
include(":data:auth")
include(":feature")
include(":feature:auth")
include(":feature:weather")
include(":feature:splash")
include(":data:weather")
