package com.poplogic.blipin.feature.onboard.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.LinkInteractionListener
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poplogic.blipin.R
import com.poplogic.blipin.nav.OnNavigateToWebView
import com.poplogic.blipin.utils.hardcoded

@Suppress("ktlint:standard:function-naming")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardBottomSheet(
    onTosCheckStateToggled: () -> Unit,
    onPrivacyCheckStateToggled: () -> Unit,
    tosCheckState: Boolean,
    privacyCheckState: Boolean,
    buttonText: String,
    onDismiss: () -> Unit,
    onLinkClicked: OnNavigateToWebView,
    onStartToUseClicked: () -> Unit,
) = Column(
    modifier =
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White),
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        IconButton(
            onClick = onDismiss,
            modifier =
                Modifier
                    .size(48.dp)
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 16.dp),
        ) {
            Image(
                painterResource(R.drawable.type_close__color_grey__size_16),
                contentDescription = "Close",
            )
        }
    }
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(124.dp)
                .padding(horizontal = 24.dp, vertical = 10.dp),
    ) {
        CheckboxWithLabel(
            modifier = Modifier.clickable(onClick = onTosCheckStateToggled),
            isCheck = tosCheckState,
            label =
                {
                    BasicText(
                        buildAnnotatedString {
                            append("我已閱讀並同意")
                            withLink(
                                LinkAnnotation.Url(
                                    "https://poplogic.github.io/terms/".hardcoded(),
                                    TextLinkStyles(
                                        style =
                                            SpanStyle(
                                                color = MaterialTheme.colorScheme.primary,
                                                textDecoration = TextDecoration.Underline,
                                            ),
                                    ),
                                    linkInteractionListener =
                                        object :
                                            LinkInteractionListener {
                                            override fun onClick(link: LinkAnnotation) {
                                                val url =
                                                    (link as? LinkAnnotation.Url)?.url ?: return
                                                onLinkClicked("Terms of Usage".hardcoded(), url)
                                            }
                                        },
                                ),
                            ) {
                                append("服務條款")
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium.copy(Color.Black),
                    )
                },
            onCheckableChanged = onTosCheckStateToggled,
        )
        Spacer(modifier = Modifier.height(8.dp))
        CheckboxWithLabel(
            isCheck = privacyCheckState,
            label =
                {
                    BasicText(
                        buildAnnotatedString {
                            append("我已閱讀並同意")
                            withLink(
                                LinkAnnotation.Url(
                                    "https://poplogic.github.io/privacy/".hardcoded(),
                                    TextLinkStyles(
                                        style =
                                            SpanStyle(
                                                color = MaterialTheme.colorScheme.primary,
                                                textDecoration = TextDecoration.Underline,
                                            ),
                                    ),
                                    linkInteractionListener =
                                        object :
                                            LinkInteractionListener {
                                            override fun onClick(link: LinkAnnotation) {
                                                val url =
                                                    (link as? LinkAnnotation.Url)?.url ?: return
                                                onLinkClicked("Privacy Policy".hardcoded(), url)
                                            }
                                        },
                                ),
                            ) {
                                append("隱私權政策")
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium.copy(Color.Black),
                    )
                },
            onCheckableChanged = onPrivacyCheckStateToggled,
        )
    }
    HorizontalDivider(
        thickness = 1.dp,
        color = Color(0xFFE7E7E7),
    )
    Spacer(modifier = Modifier.height(20.dp))

    Box(modifier = Modifier.padding(horizontal = 20.dp)) {
        Button(
            onClick = {
                if (tosCheckState && privacyCheckState) {
                    onStartToUseClicked()
                }
            },
            colors =
                if (tosCheckState && privacyCheckState) {
                    ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6600),
                        contentColor = Color.White,
                    )
                } else {
                    ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6600).copy(alpha = 0.5f),
                        contentColor = Color.White.copy(alpha = 0.5f),
                    )
                },
            shape = RoundedCornerShape(30),
            modifier =
                Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
        ) {
            Text(
                text = buttonText,
                fontSize = 16.sp,
                lineHeight = 26.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center,
                color = Color.White,
            )
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}
