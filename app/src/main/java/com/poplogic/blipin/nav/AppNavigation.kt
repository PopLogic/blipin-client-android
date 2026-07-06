package com.poplogic.blipin.nav

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.poplogic.blipin.common_ui.WebViewScreen
import com.poplogic.blipin.feature.authenticate.presentation.AuthenticationScreen
import com.poplogic.blipin.feature.home.presentation.HomePageScreen
import com.poplogic.blipin.feature.onboard.presentation.OnboardPageScreen
import com.poplogic.blipin.feature.splash.presentation.SplashScreen
import com.poplogic.blipin.nav.Routes.HOME
import com.poplogic.blipin.nav.Routes.ONBOARD
import com.poplogic.blipin.nav.Routes.SPLASH
import com.poplogic.blipin.nav.Routes.WEB_VIEW

object Routes {
    const val ONBOARD = "onboard"
    const val HOME = "home"
    const val WEB_VIEW = "webview"
    const val SPLASH = "splash"
    const val AUTHENTICATE = "authenticate"
}

typealias OnNavigateToWebView = (screenTitle: String, url: String) -> Unit
typealias OnNavigateToHome = () -> Unit

@Composable
fun AppNavigation(
    hideSystemBars: () -> Unit,
    showSystemBars: () -> Unit,
    appNavigationController: NavHostController,
) {
    NavHost(
        navController = appNavigationController,
        startDestination = SPLASH,
    ) {
        composable(Routes.AUTHENTICATE) {
            hideSystemBars()
            AuthenticationScreen()
        }

        composable(SPLASH) {
            hideSystemBars()
            SplashScreen(
                onNavigateToOnboard = {
                    // Prevent duplicate navigate calls from repeated splash callbacks.
                    if (appNavigationController.currentBackStackEntry?.destination?.route == SPLASH) {
                        appNavigationController.navigate(ONBOARD) {
                            popUpTo(appNavigationController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                },
            )
        }

        composable(HOME) {
            showSystemBars()
            HomePageScreen(appNavigationController = appNavigationController)
        }

        composable(ONBOARD) {
            OnboardPageScreen(
                viewModel = viewModel(),
                onNavigateToWebView = { screenTitle, url ->
                    val encodedUrl = Uri.encode(url)
                    appNavigationController.navigate("$WEB_VIEW?title=$screenTitle&url=$encodedUrl")
                },
                onNavigateToHome = {
                    appNavigationController.navigate(HOME) {
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
            WebViewScreen(
                url = url,
                screenTitle = title,
                onBack = { appNavigationController.popBackStack() },
            )
        }
    }
}
