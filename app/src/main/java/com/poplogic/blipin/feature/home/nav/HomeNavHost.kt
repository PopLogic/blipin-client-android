package com.poplogic.blipin.feature.home.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.poplogic.blipin.feature.home.presentation.HomePageTabs
import com.poplogic.blipin.feature.profile.presentation.ProfileScreen

@Composable
fun HomeNavHost(
    navController: androidx.navigation.NavHostController,
    startDestination: HomePageTabs,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier,
    ) {
        composable(HomePageTabs.Explore.route) {
            Box {}
//            ExploreScreen()
        }
        composable(HomePageTabs.Favorites.route) {
            Box {}
//            FavoritesScreen()
        }
        composable(HomePageTabs.Profile.route) {
            ProfileScreen(
                modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.safeDrawing),
                viewModel(),
            )
        }
    }
}
