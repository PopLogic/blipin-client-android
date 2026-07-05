package com.poplogic.blipin.feature.home.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.poplogic.blipin.common_ui.background.Background
import com.poplogic.blipin.feature.home.nav.HomeNavHost
import com.poplogic.blipin.ui.theme.BlipinBlack
import com.poplogic.blipin.ui.theme.BlipinBrandPrimary
import com.poplogic.blipin.ui.theme.BlipinNeutral50
import com.poplogic.blipin.ui.theme.BlipinPrimary100
import com.poplogic.blipin.ui.theme.Typography

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePageScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val startDestination = HomePageTabs.Explore
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    Background {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                NavigationBar(
                    containerColor = BlipinNeutral50,
                ) {
                    HomePageTabs.entries.forEachIndexed { index, homepage ->
                        val selected = selectedDestination == index
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
                                navController.navigate(route = homepage.route)
                                selectedDestination = index
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
            HomeNavHost(navController, startDestination, contentPadding)
        }
    }
}
