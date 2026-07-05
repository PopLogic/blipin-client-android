package com.poplogic.blipin.feature.home.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.poplogic.blipin.common_ui.background.CollapsingHeaderScreen
import com.poplogic.blipin.feature.home.presentation.HomePageTabs
import com.poplogic.blipin.feature.profile.presentation.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavHost(
    navController: NavHostController,
    startDestination: HomePageTabs,
    contentPaddingValues: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = Modifier,
    ) {
        composable(HomePageTabs.Explore.route) {
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            CollapsingHeaderScreen(
                contentPaddingValues,
            )

//            ExploreScreen()
        }
        composable(HomePageTabs.Favorites.route) {
            Box {}
//            FavoritesScreen()
        }
        composable(HomePageTabs.Profile.route) {
            ProfileScreen(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(contentPaddingValues),
                viewModel(),
            )
        }
    }
}
