package com.poplogic.blipin.feature.profile.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.poplogic.blipin.R
import com.poplogic.blipin.common_ui.button.ButtonPrimary
import com.poplogic.blipin.ui.theme.BlipinBlack
import com.poplogic.blipin.ui.theme.BlipinNeutral50
import com.poplogic.blipin.ui.theme.BlipinWhite
import com.poplogic.blipin.ui.theme.Palette
import com.poplogic.blipin.ui.theme.Typography
import com.poplogic.blipin.utils.hardcoded

@Composable
fun UserCard(modifier: Modifier = Modifier) {
    Card(
        modifier =
            modifier
                .height(138.dp)
                .padding(horizontal = 20.dp, vertical = 10.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BlipinWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row {
            Box(
                modifier =
                    Modifier
                        .weight(60f)
                        .background(color = Palette.Neutral.neutral50)
                        .clip(CircleShape)
                        .align(Alignment.CenterVertically),
            ) {
                Icon(
                    painter = painterResource(R.drawable.avatar_default),
                    contentDescription = "Default Avatar".hardcoded(),
                    modifier = Modifier.fillMaxSize(),
                    tint = Palette.Neutral.neutral200,
                )
            }
            Spacer(modifier = Modifier.weight(10f))
            Column(modifier = Modifier.weight(205f)) {
                Text(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    text = "登入收藏你喜歡的餐車".hardcoded(),
                    style =
                        Typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = BlipinBlack,
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                        ),
                )
                Text(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    text = "追蹤餐車出沒地點,不再錯過".hardcoded(),
                    style =
                        Typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal,
                            color = Palette.Neutral.neutral500,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                        ),
                )
            }
            Spacer(modifier = Modifier.weight(20f))
        }
        Spacer(modifier = Modifier.height(10.dp))
        ButtonPrimary(
            onClick = {},
            text = "登入/註冊".hardcoded(),
            modifier =
                Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
        )
    }
}

@Composable
fun UserCard(
    modifier: Modifier = Modifier,
    userName: String,
    userEmail: String,
    userProfilePictureUrl: String,
    birthdayString: String,
) {
    Card(
        modifier =
            modifier
                .height(120.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BlipinWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier =
                    Modifier
                        .weight(68f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImage(
                    model = userProfilePictureUrl,
                    contentDescription = "User Profile Picture".hardcoded(),
                    modifier =
                        Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                    clipToBounds = true,
                    contentScale = ContentScale.Fit,
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = userName,
                    style =
                        Typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = BlipinBlack,
                            textAlign = TextAlign.Center,
                        ),
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .weight(219f),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                UserInfoWidget(
                    infoTitle = "Email".hardcoded(),
                    infoValue = userEmail,
                )
                HorizontalDivider(
                    modifier = Modifier.height(1.dp),
                    color = BlipinNeutral50,
                )
                UserInfoWidget(
                    infoTitle = "Birthday".hardcoded(),
                    infoValue = birthdayString,
                )
            }
        }
    }
}
