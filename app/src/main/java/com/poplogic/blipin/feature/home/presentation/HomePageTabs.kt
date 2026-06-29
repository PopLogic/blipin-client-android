package com.poplogic.blipin.feature.home.presentation

import androidx.annotation.DrawableRes
import com.poplogic.blipin.R
import com.poplogic.blipin.utils.hardcoded

enum class HomePageTabs(
    val route: String,
    val label: String,
    val contentDescription: String,
    @field:DrawableRes val iconRes: Int,
    @field:DrawableRes val activatedIconRes: Int,
) {
    Explore(
        "explore",
        "explore".hardcoded(),
        "Explore".hardcoded(),
        R.drawable.explore,
        R.drawable.explore_activated,
    ),
    Favorites(
        "favorites",
        "favorites".hardcoded(),
        "Favorites".hardcoded(),
        R.drawable.favorite,
        R.drawable.favorite_activated,
    ),
    Profile(
        "profile",
        "profile".hardcoded(),
        "Profile".hardcoded(),
        R.drawable.profile,
        R.drawable.profile_activated,
    ),
}
