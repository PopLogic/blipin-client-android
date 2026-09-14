package com.poplogic.blipin.presentation.favorites.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.poplogic.blipin.R
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.feature.common.theme.Typography
import com.poplogic.blipin.feature.common.theme.actionButton
import com.poplogic.blipin.presentation.home.presentation.HomePageTabs

@Composable
fun FavoritesPageSuccessScreen(
    modifier: Modifier = Modifier,
    homeNavigationController: NavController,
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
            text = "尚未收藏任何餐車".hardcoded(),
            style =
                Typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = Palette.Neutral.neutral950,
                ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text =
                "收藏喜歡的餐車\n" +
                    "出攤、營業時間更新時第一時間通知你，\n" +
                    "不再白跑一趟".hardcoded(),
            style =
                Typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = Palette.Neutral.neutral600,
                ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextButton(
            modifier =
                Modifier
                    .background(color = Color.Transparent)
                    .padding(vertical = 12.dp)
                    .wrapContentHeight(),
            onClick = {
                homeNavigationController.navigate(HomePageTabs.Explore.route)
            },
        ) {
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "前往探索".hardcoded(),
                    style =
                        Typography.actionButton.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            color = Palette.Primary.brand,
                        ),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.nav_icon_right_small),
                    contentDescription = "Arrow Right",
                    tint = Palette.Primary.brand,
                )
            }
        }
    }
}
