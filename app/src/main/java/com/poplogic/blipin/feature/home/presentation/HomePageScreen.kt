package com.poplogic.blipin.feature.home.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.poplogic.blipin.common_ui.background.Background
import com.poplogic.blipin.feature.home.nav.HomeNavHost
import com.poplogic.blipin.ui.theme.BlipinBlack
import com.poplogic.blipin.ui.theme.BlipinBrandPrimary
import com.poplogic.blipin.ui.theme.BlipinNeutral50
import com.poplogic.blipin.ui.theme.BlipinPrimary100
import com.poplogic.blipin.ui.theme.Typography
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePageScreen(
    modifier: Modifier = Modifier,
    appNavigationController: NavHostController,
    navController: NavHostController = rememberNavController(),
) {
    val startDestination = HomePageTabs.Explore
    val coroutineScope = rememberCoroutineScope()

    // Scroll states hoisted here so the bottom bar can trigger scroll-to-top
    val exploreListState: LazyListState = rememberLazyListState()
    val favoritesScrollState = rememberScrollState()
    val profileScrollState = rememberScrollState()

    // Derive the selected tab directly from the NavController back stack so that
    // system back-button presses automatically update the bottom bar highlight.
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Background {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                NavigationBar(
                    containerColor = BlipinNeutral50,
                ) {
                    HomePageTabs.entries.forEachIndexed { index, homepage ->
                        val selected = currentRoute == homepage.route
                        NavigationBarItem(
                            selected = selected,
                            colors =
                                NavigationBarItemDefaults.colors(
                                    selectedIconColor = BlipinBrandPrimary,
                                    unselectedIconColor = BlipinBlack,
                                    indicatorColor = BlipinPrimary100,
                                    selectedTextColor = BlipinBrandPrimary,
                                    unselectedTextColor = BlipinBlack,
                                ),
                            onClick = {
                                if (selected) {
                                    // Already on this tab — scroll to top
                                    coroutineScope.launch {
                                        when (homepage) {
                                            HomePageTabs.Explore -> exploreListState.animateScrollToItem(0)
                                            HomePageTabs.Favorites -> favoritesScrollState.animateScrollTo(0)
                                            HomePageTabs.Profile -> profileScrollState.animateScrollTo(0)
                                        }
                                    }
                                    return@NavigationBarItem
                                }
                                navController.navigate(route = homepage.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = false
                                }
                            },
                            icon = {
                                Icon(
                                    painterResource(id = if (selected) homepage.activatedIconRes else homepage.iconRes),
                                    contentDescription = homepage.contentDescription,
                                    modifier = Modifier.size(24.dp),
                                )
                            },
                            label = { Text(homepage.label, style = Typography.labelMedium) },
                        )
                    }
                }
            },
        ) { contentPadding ->
            HomeNavHost(
                navController = navController,
                startDestination = startDestination,
                contentPaddingValues = contentPadding,
                appNavigationController = appNavigationController,
                exploreListState = exploreListState,
                favoritesScrollState = favoritesScrollState,
                profileScrollState = profileScrollState,
            )
        }
    }
}
