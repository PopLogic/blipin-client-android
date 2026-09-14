package com.poplogic.blipin.presentation.home.nav

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.poplogic.blipin.presentation.explore.presentation.ExploreScreen
import com.poplogic.blipin.presentation.explore.presentation.ExploreViewModel
import com.poplogic.blipin.presentation.favorites.presentation.FavoritesScreen
import com.poplogic.blipin.presentation.home.presentation.HomePageTabs
import com.poplogic.blipin.presentation.profile.presentation.ProfileScreen
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
        enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing))
        },
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
                homeNavigationController = navController,
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
