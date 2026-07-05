package com.poplogic.blipin.nav

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.poplogic.blipin.common_ui.WebViewScreen
import com.poplogic.blipin.feature.home.presentation.HomePageScreen
import com.poplogic.blipin.feature.onboard.presentation.OnboardPageScreen
import com.poplogic.blipin.feature.splash.presentation.SplashScreen
import com.poplogic.blipin.nav.Routes.EXPLORE_TAB
import com.poplogic.blipin.nav.Routes.FAVORITE_TAB
import com.poplogic.blipin.nav.Routes.HOME
import com.poplogic.blipin.nav.Routes.ONBOARD
import com.poplogic.blipin.nav.Routes.PROFILE_TAB
import com.poplogic.blipin.nav.Routes.SPLASH
import com.poplogic.blipin.nav.Routes.WEB_VIEW

object Routes {
    const val ONBOARD = "onboard"
    const val HOME = "home"
    const val WEB_VIEW = "webview"
    const val SPLASH = "splash"
    const val EXPLORE_TAB = "explore_tab"
    const val FAVORITE_TAB = "favorite_tab"
    const val PROFILE_TAB = "profile_tab"
}

typealias OnNavigateToWebView = (screenTitle: String, url: String) -> Unit
typealias OnNavigateToHome = () -> Unit

@Composable
fun AppNavigation(
    hideSystemBars: () -> Unit,
    showSystemBars: () -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SPLASH,
    ) {
        composable(SPLASH) {
            hideSystemBars()
            SplashScreen(
                onNavigateToOnboard = {
                    // Prevent duplicate navigate calls from repeated splash callbacks.
                    if (navController.currentBackStackEntry?.destination?.route == SPLASH) {
                        navController.navigate(ONBOARD) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
            )
        }

        composable(HOME) {
            showSystemBars()
            HomePageScreen()
        }

        composable(EXPLORE_TAB) {
            Box {}
        }
        composable(FAVORITE_TAB) {
            Box {}
        }
        composable(PROFILE_TAB) {
            Box {}
        }

        composable(ONBOARD) {
            OnboardPageScreen(
                viewModel = viewModel(),
                onNavigateToWebView = { screenTitle, url ->
                    val encodedUrl = Uri.encode(url)
                    navController.navigate("$WEB_VIEW?title=$screenTitle&url=$encodedUrl")
                },
                onNavigateToHome = {
                    navController.navigate(HOME) {
                        popUpTo(ONBOARD) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )
        }

        composable("${WEB_VIEW}?title={title}&url={url}") { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("url").orEmpty()
            val title = backStackEntry.arguments?.getString("title").orEmpty()
            val url = Uri.decode(encodedUrl)
            WebViewScreen(url = url, screenTitle = title, onBack = { navController.popBackStack() })
        }
    }
}
