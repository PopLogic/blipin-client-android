package com.poplogic.blipin.nav

import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.poplogic.blipin.domain.user.model.UserEntity
import com.poplogic.blipin.feature.common.WebViewScreen
import com.poplogic.blipin.feature.common.route.Routes
import com.poplogic.blipin.feature.common.route.Routes.HOME
import com.poplogic.blipin.feature.common.route.Routes.ONBOARD
import com.poplogic.blipin.feature.common.route.Routes.SPLASH
import com.poplogic.blipin.feature.common.route.Routes.WEB_VIEW
import com.poplogic.blipin.presentation.authenticate.presentation.AuthenticationScreen
import com.poplogic.blipin.presentation.home.presentation.HomePageScreen
import com.poplogic.blipin.presentation.map.presentation.MapScreen
import com.poplogic.blipin.presentation.onboard.presentation.OnboardPageScreen
import com.poplogic.blipin.presentation.profile.presentation.account.AccountScreen
import com.poplogic.blipin.presentation.profile.presentation.comment.CommentScreen
import com.poplogic.blipin.presentation.profile.presentation.comment.CommentScreenViewModel
import com.poplogic.blipin.presentation.profile.presentation.developer.DeveloperScreen
import com.poplogic.blipin.presentation.profile.presentation.notification.NotificationScreen
import com.poplogic.blipin.presentation.profile.presentation.place.PlaceScreen
import com.poplogic.blipin.presentation.profile.presentation.support.SupportScreen
import com.poplogic.blipin.presentation.profile.presentation.user.ProfileEditScreen
import com.poplogic.blipin.presentation.splash.presentation.SplashScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

object SavedStateKeys {
    const val USER_ENTITY = "userEntity"
}

typealias OnNavigateToWebView = (screenTitle: String, url: String) -> Unit
typealias OnNavigateToHome = () -> Unit

fun NavController.navigateToProfileEdit(userEntity: UserEntity) {
    currentBackStackEntry?.savedStateHandle?.set(SavedStateKeys.USER_ENTITY, userEntity)
    navigate(Routes.PROFILE_EDIT)
}

fun NavController.navigateToMyPlaces() {
    navigate(Routes.USER_PLACE)
}

fun NavController.navigateToMyComments() {
    navigate(Routes.USER_COMMENT)
}

@Composable
fun AppNavigation(
    hideSystemBars: () -> Unit,
    showSystemBars: () -> Unit,
    appNavigationController: NavHostController,
) {
    NavHost(
        navController = appNavigationController,
        startDestination = SPLASH,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300),
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300),
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300),
            )
        },
    )
    {
        composable(Routes.AUTHENTICATE) {
            hideSystemBars()
            AuthenticationScreen(appNavigationController = appNavigationController)
        }
        composable(Routes.MAP) {
            hideSystemBars()
            MapScreen()
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

        composable(Routes.CONNECTED_ACCOUNT) {
            AccountScreen(
                onBack = { appNavigationController.popBackStack() },
                viewModel = koinViewModel(),
            )
        }
        composable(Routes.NOTIFICATION_SETTING) {
            NotificationScreen(
                onBack = { appNavigationController.popBackStack() },
                viewModel = koinViewModel(),
            )
        }
        composable(Routes.SUPPORT) {
            SupportScreen(
                onBack = { appNavigationController.popBackStack() },
                viewModel = koinViewModel(),
            )
        }
        composable(Routes.PROFILE_EDIT) { backStackEntry ->
            val currentUserEntity =
                backStackEntry.savedStateHandle.get<UserEntity>(SavedStateKeys.USER_ENTITY)
            val previousUserEntity =
                appNavigationController.previousBackStackEntry?.savedStateHandle?.get<UserEntity>(
                    SavedStateKeys.USER_ENTITY,
                )

            LaunchedEffect(currentUserEntity, previousUserEntity) {
                if (currentUserEntity == null && previousUserEntity != null) {
                    backStackEntry.savedStateHandle[SavedStateKeys.USER_ENTITY] = previousUserEntity
                }
            }

            ProfileEditScreen(
                onBack = { appNavigationController.popBackStack() },
                viewModel =
                    koinViewModel(parameters = {
                        parametersOf(
                            currentUserEntity ?: previousUserEntity,
                        )
                    }),
            )
        }
        composable(Routes.USER_PLACE) {
            // UserPlaceScreen(appNavigationController = appNavigationController)
            PlaceScreen(
                onBack = { appNavigationController.popBackStack() },
                viewModel = koinViewModel(),
            )
        }
        composable(Routes.USER_COMMENT) {
            CommentScreen(
                onBack = { appNavigationController.popBackStack() },
                viewModel = koinViewModel<CommentScreenViewModel>(),
            )
        }
        composable(Routes.BLIPIN_DEVELOPER) {
            DeveloperScreen(
                onBack = { appNavigationController.popBackStack() },
                viewModel = koinViewModel(),
            )
        }
    }
}
