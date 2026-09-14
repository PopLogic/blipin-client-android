package com.poplogic.blipin.common.base.environment

import android.util.Log
import com.poplogic.blipin.common.base.BuildConfig

const val WEB_CLIENT_ID_2 =
    "223514493620-ncsrlkhgn0j5lel1vp9d467ppngvvkna.apps.googleusercontent.com"
const val WEB_CLIENT_ID =
    "223514493620-4nqrihmolgsv3spkdsp1qd4cbofv1k53.apps.googleusercontent.com"

enum class Flavor {
    DEVELOPMENT,
    STAGING,
    PRODUCTION,
    ;

    companion object {
        fun fromBuildConfig(): Flavor {
            when (BuildConfig.FLAVOR) {
                "development" -> {
                    return DEVELOPMENT
                }

                "staging" -> {
                    return STAGING
                }

                "production" -> {
                    return PRODUCTION
                }

                else -> {
                    Log.e(
                        "Flavor",
                        "Unknown build flavor: ${BuildConfig.FLAVOR}, defaulting to DEVELOPMENT",
                    )
                    return DEVELOPMENT
                }
            }
        }
    }
}

enum class Environment(
    val backendHost: String,
) {
    DEVELOPMENT(
        backendHost = "https://superjet-diner-prism.ngrok-free.dev",
    ),
    STAGING(
        backendHost = "https://superjet-diner-prism.ngrok-free.dev",
    ),
    PRODUCTION(
        backendHost = "https://blipin.com",
    ),
    ;

    companion object {
        fun fromBuildConfig(): Environment {
            when (BuildConfig.FLAVOR) {
                "development" -> {
                    return DEVELOPMENT
                }

                "staging" -> {
                    return STAGING
                }

                "production" -> {
                    return PRODUCTION
                }

                else -> {
                    Log.e(
                        "Flavor",
                        "Unknown build flavor: ${BuildConfig.FLAVOR}, defaulting to DEVELOPMENT",
                    )
                    return Environment.DEVELOPMENT
                }
            }
        }
    }
}
