package com.poplogic.blipin.feature.home.nav

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.poplogic.blipin.feature.explore.presentation.ExploreScreen
import com.poplogic.blipin.feature.explore.presentation.ExploreViewModel
import com.poplogic.blipin.feature.favorites.presentation.FavoritesScreen
import com.poplogic.blipin.feature.home.presentation.HomePageTabs
import com.poplogic.blipin.feature.profile.presentation.ProfileScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavHost(
    navController: NavHostController,
    startDestination: HomePageTabs,
    contentPaddingValues: PaddingValues,
    appNavigationController: NavHostController,
    exploreListState: LazyListState,
    favoritesScrollState: ScrollState,
    profileScrollState: ScrollState,
) {
    val exploreViewModel: ExploreViewModel = koinViewModel<ExploreViewModel>()
    val snackbarHostState = koinInject<SnackbarHostState>()
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = Modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        composable(HomePageTabs.Explore.route) {
            ExploreScreen(
                contentPaddingValues,
                viewModel = exploreViewModel,
                snackbarHostState = snackbarHostState,
                appNavigationController = appNavigationController,
                scrollState = exploreListState,
            )
        }
        composable(HomePageTabs.Favorites.route) {
            FavoritesScreen(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(contentPaddingValues),
                appNavigationController = appNavigationController,
                scrollState = favoritesScrollState,
            )
        }
        composable(HomePageTabs.Profile.route) {
            ProfileScreen(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(contentPaddingValues),
                appNavigationController = appNavigationController,
                scrollState = profileScrollState,
            )
        }
    }
}
