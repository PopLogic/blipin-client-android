package com.poplogic.blipin.feature.onboard.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poplogic.blipin.R
import com.poplogic.blipin.feature.onboard.presentation.composables.OnboardBottomSheet
import com.poplogic.blipin.feature.onboard.presentation.composables.OnboardPage
import com.poplogic.blipin.feature.onboard.presentation.composables.PagerDotIndicator
import com.poplogic.blipin.feature.onboard.presentation.viewmodel.OnboardUiModel
import com.poplogic.blipin.feature.onboard.presentation.viewmodel.OnboardViewModel
import com.poplogic.blipin.nav.OnNavigateToHome
import com.poplogic.blipin.nav.OnNavigateToWebView
import kotlinx.coroutines.launch

@Suppress("ktlint:standard:function-naming")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardPageScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardViewModel,
    onNavigateToWebView: OnNavigateToWebView,
    onNavigateToHome: OnNavigateToHome,
) {
    val uiState = viewModel.uiState.collectAsState()
    val pagerState =
        rememberPagerState(pageCount = {
            uiState.value.tabs.size
        })
    val colorStops =
        arrayOf(
            0.0f to Color.White,
            1.0f to Color(0xFFFFE0C0),
        )
    val animationScope = rememberCoroutineScope()
    var shouldShowBottomSheet by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val tosCheckState = rememberSaveable { mutableStateOf(false) }
    val privacyCheckState = rememberSaveable { mutableStateOf(false) }
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Brush.radialGradient(colorStops = colorStops)),
    ) {
        HorizontalPager(
            state = pagerState,
            modifier =
                Modifier
                    .fillMaxHeight()
                    .windowInsetsPadding(WindowInsets.safeDrawing),
        ) { pageIndex ->
            Column {
                val tab = uiState.value.tabs[pageIndex]
                OnboardPage(
                    title = tab.title,
                    subtitle = tab.description,
                    lottieRawRes = tab.lottieRes,
                    modifier = modifier.weight(.64f),
                )
                Spacer(modifier = modifier.weight(.083f))

                Box(
                    modifier =
                        Modifier.weight(0.277f),
                ) {
                    val bottomWaveImage =
                        if (pageIndex == 1) R.drawable.onboarding_wave_2 else R.drawable.onboarding_wave_1
                    Image(
                        painterResource(bottomWaveImage),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomStart),
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier =
                            Modifier
                                .align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                    ) {
                        Spacer(Modifier.height(94.dp))
                        Button(
                            onClick = {
                                if (pagerState.targetPage == uiState.value.tabs.size - 1) {
                                    shouldShowBottomSheet = true
                                } else {
                                    animationScope.launch {
                                        pagerState.animateScrollToPage(pagerState.targetPage + 1)
                                    }
                                }
                            },
                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFF6600),
                                    contentColor = Color.White,
                                ),
                            shape = RoundedCornerShape(30),
                            modifier =
                                Modifier
                                    .height(48.dp)
                                    .fillMaxWidth(),
                        ) {
                            Text(
                                text = uiState.value.tabs[pagerState.targetPage].buttonText,
                                fontSize = 16.sp,
                                lineHeight = 26.sp,
                                fontWeight = FontWeight.W500,
                                textAlign = TextAlign.Center,
                                color = Color.White,
                            )
                        }
                        Spacer(Modifier.height(24.dp))
                    }
                }
            }
        }

        Column(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .background(Color.Transparent),
        ) {
            Spacer(modifier = Modifier.weight(0.64f))
            Box(
                modifier =
                    Modifier
                        .weight(0.083f)
                        .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                PagerDotIndicator(
                    pageCount = uiState.value.tabs.size,
                    currentPage = pagerState.currentPage,
                )
            }
            Spacer(modifier = Modifier.weight(0.277f))
        }
        if (shouldShowBottomSheet) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    shouldShowBottomSheet = false
                },
                dragHandle = null,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                tonalElevation = 5.dp,
            ) {
                OnboardBottomSheet(
                    tosCheckState = tosCheckState.value,
                    privacyCheckState = privacyCheckState.value,
                    onTosCheckStateToggled = {
                        tosCheckState.value = !tosCheckState.value
                    },
                    onPrivacyCheckStateToggled = {
                        privacyCheckState.value = !privacyCheckState.value
                    },
                    buttonText = uiState.value.tabs[pagerState.targetPage].buttonText,
                    onDismiss = {
                        shouldShowBottomSheet = false
                    },
                    onLinkClicked = onNavigateToWebView,
                    onStartToUseClicked = {
                        shouldShowBottomSheet = false
                        onNavigateToHome()
                    },
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
fun OnboardPagePreview() {
    val mockOnboardUiModel =
        OnboardUiModel(
            title = "隨時隨地\n找到你想吃的餐車",
            description = "Blipin 帶你發現附近的移動美味",
            buttonText = "下一步",
            lottieRes = R.raw.intro_1,
        )
    OnboardPage(
        modifier = Modifier,
        title = mockOnboardUiModel.title,
        subtitle = mockOnboardUiModel.description,
        lottieRawRes = mockOnboardUiModel.lottieRes,
    )
}
