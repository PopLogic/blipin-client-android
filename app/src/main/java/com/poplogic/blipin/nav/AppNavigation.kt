package com.poplogic.blipin.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Serializable
object Onboard

@Serializable
object Home

@Serializable
object WebView

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

//    NavHost(
//        navController = navController,
//        startDestination = NavRoute.Onboard.route,
//    ) {
//        onboardGraph(navController)
//        homeGraph(navController)
//    }
}
