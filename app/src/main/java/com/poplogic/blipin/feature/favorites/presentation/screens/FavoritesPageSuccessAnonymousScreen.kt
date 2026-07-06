package com.poplogic.blipin.feature.favorites.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.poplogic.blipin.R
import com.poplogic.blipin.common_ui.button.ButtonPrimary
import com.poplogic.blipin.nav.Routes
import com.poplogic.blipin.ui.theme.Palette
import com.poplogic.blipin.ui.theme.Typography
import com.poplogic.blipin.utils.hardcoded

@Composable
fun FavoritesPageSuccessAnonymousScreen(
    modifier: Modifier = Modifier,
    appNavigationController: NavController,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(150 / 343f),
        ) {
            Image(
                painter = painterResource(id = R.drawable.favorites_anonymous_placeholder),
                contentDescription = "Favorites Empty",
                contentScale = ContentScale.FillWidth,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "登入後即可查看您的收藏清單".hardcoded(),
            style =
                Typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = Palette.Neutral.neutral950,
                ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonPrimary(
            onClick = {
                appNavigationController.navigate(Routes.AUTHENTICATE)
            },
            text = "登入/註冊".hardcoded(),
            modifier =
                Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
        )
    }
}
