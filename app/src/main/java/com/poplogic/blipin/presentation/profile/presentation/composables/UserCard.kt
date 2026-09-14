package com.poplogic.blipin.presentation.profile.presentation.composables

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poplogic.blipin.R
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.domain.user.model.UserEntity
import com.poplogic.blipin.feature.common.avatar.AnonymousAvatar
import com.poplogic.blipin.feature.common.avatar.Avatar
import com.poplogic.blipin.feature.common.button.ButtonPrimary
import com.poplogic.blipin.feature.common.theme.BlipinBlack
import com.poplogic.blipin.feature.common.theme.BlipinWhite
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.feature.common.theme.Typography
import com.poplogic.blipin.presentation.common.throttleClick
import kotlinx.datetime.LocalDate

@Composable
fun UserCard(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,
) {
    Card(
        modifier =
            modifier
                .wrapContentHeight(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BlipinWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .wrapContentHeight()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp),
            ) {
                Box(
                    modifier =
                        Modifier
                            .weight(60f)
                            .background(color = Palette.White)
                            .align(Alignment.CenterVertically),
                ) {
                    AnonymousAvatar(
                        modifier =
                            Modifier
                                .size(48.dp)
                                .align(Alignment.Center),
                    )
                }
                Spacer(modifier = Modifier.weight(10f))
                Column(
                    modifier =
                        Modifier
                            .weight(205f)
                            .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
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
                                textAlign = TextAlign.Start,
                            ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
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
                                textAlign = TextAlign.Start,
                            ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Spacer(modifier = Modifier.weight(20f))
            }
            Spacer(modifier = Modifier.height(10.dp))
            ButtonPrimary(
                onClick = {
                    onSignInClick()
                },
                text = "登入/註冊".hardcoded(),
                modifier =
                    Modifier
                        .height(48.dp)
                        .fillMaxWidth(),
            )
        }
    }
}

@Composable
fun UserCard(
    modifier: Modifier = Modifier,
    userEntity: UserEntity,
    onEditProfileClick: (UserEntity) -> Unit,
    onMyCommentClick: () -> Unit,
    onMyPlacesClick: () -> Unit,
) {
    Card(
        modifier =
            modifier
                .height(128.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BlipinWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(80f)
                        .padding(start = 20.dp, end = 30.dp, top = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Avatar(
                    modifier =
                        Modifier
                            .weight(48f)
                            .clip(CircleShape),
                    userProfileImageUrl = userEntity.profilePictureUrl,
                )

                Text(
                    modifier = Modifier.weight(207f),
                    text = userEntity.displayName ?: "User".hardcoded(),
                    style =
                        Typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = BlipinBlack,
                            textAlign = TextAlign.Start,
                        ),
                )
                IconButton(
                    onClick = {
                        onEditProfileClick(userEntity)
                    },
                    modifier = Modifier.weight(18f),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_edit),
                        contentDescription = "Edit Profile".hardcoded(),
                        tint = Palette.Neutral.neutral500,
                    )
                }
            }
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(48f)
                        .shadow(
                            shape = RectangleShape,
                            elevation = .5.dp,
                        ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UserCardButton(
                    modifier = Modifier.weight(1f),
                    iconRes = R.drawable.icon_star,
                    text = "我的留言".hardcoded(),
                    onClick = {
                        onMyCommentClick()
                    },
                )
                VerticalDivider(
                    modifier =
                        Modifier
                            .fillMaxHeight()
                            .width(1.dp),
                    color = Palette.Neutral.neutral100,
                )
                UserCardButton(
                    modifier = Modifier.weight(1f),
                    iconRes = R.drawable.icon_location_indicator,
                    text = "常用地點".hardcoded(),
                    onClick = {
                        onMyPlacesClick()
                    },
                )
            }
        }
    }
}

@Composable
private fun UserCardButton(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    text: String,
    onClick: (() -> Unit),
) {
    Row(
        modifier =
            modifier
                .fillMaxHeight()
                .padding(horizontal = 20.dp)
                .throttleClick(
                    onClick = onClick,
                ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = "My Comment".hardcoded(),
            tint = Palette.Primary.brand,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style =
                Typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = Palette.Neutral.neutral500,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Start,
                ),
        )
    }
}

@Preview
@Composable
fun UserCardPreview() {
    UserCard(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        userEntity =
            UserEntity(
                displayName = "John Doe",
                profilePictureUrl = null,
                birthdate = LocalDate.parse("1990-01-01"),
                gender = UserEntity.Gender.MALE,
            ),
        onEditProfileClick = {},
        onMyPlacesClick = {},
        onMyCommentClick = {},
    )
}
