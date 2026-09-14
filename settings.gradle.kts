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

@Suppress("UnstableApiUsage")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Blipin"
include(":app")
include(":api:auth")
include(":api:user")
include(":common:base")
include(":api:token")
include(":api:common")
include(":data:user")
include(":domain:user")
include(":domain:auth")
include(":domain:common")
include(":data:token")
include(":data:auth")
include(":domain:location")
include(":domain:connectivity")
include(":feature:common")
