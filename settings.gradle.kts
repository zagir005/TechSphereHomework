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
include(":feature")
include(":feature:auth")
include(":feature:splash")
include(":data:weather")
include(":data:news")
include(":feature:client")
include(":feature:client:news")
include(":feature:client:news:articledetails")
include(":feature:client:news:favorite")
include(":feature:client:news:latest")
include(":feature:client:weather")
include(":feature:admin")
include(":feature:admin:dashboard")
include(":feature:admin:users")
include(":feature:admin:dashboard:root")
include(":feature:admin:dashboard:home")
include(":feature:admin:dashboard:tariff")
include(":feature:admin:dashboard:computer")
include(":feature:admin:dashboard:session")
include(":feature:admin:dashboard:computer:list")
include(":feature:admin:dashboard:computer:addoredit")
include(":feature:admin:dashboard:session:add")
include(":feature:admin:dashboard:session:finish")
include(":feature:admin:dashboard:tariff:list")
include(":feature:admin:dashboard:tariff:addoredit")
include(":feature:client:root")
include(":feature:admin:root")
include(":feature:admin:users:list")
include(":data:user")
include(":domain:user")
include(":feature:admin:users:addoredit")
include(":core:authmanager")
include(":domain:computer")
include(":data:computer")
