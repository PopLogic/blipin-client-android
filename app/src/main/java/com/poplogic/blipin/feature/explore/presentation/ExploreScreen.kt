package com.poplogic.blipin.feature.explore.presentation

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.poplogic.blipin.R
import com.poplogic.blipin.common_ui.background.ExploreTopBackground
import com.poplogic.blipin.common_ui.snack_bar.BottomSnackbar
import com.poplogic.blipin.common_ui.snack_bar.LoginSuggestionSnackBarData
import com.poplogic.blipin.common_ui.snack_bar.showNetworkIssueSnackBar
import com.poplogic.blipin.feature.explore.presentation.sections.BrandingSection
import com.poplogic.blipin.feature.explore.presentation.sections.FilterChipsSection
import com.poplogic.blipin.feature.explore.presentation.sections.HotSection
import com.poplogic.blipin.feature.explore.presentation.sections.NewStoreSection
import com.poplogic.blipin.feature.explore.presentation.sections.exploreSection
import com.poplogic.blipin.nav.Routes
import com.poplogic.blipin.ui.theme.Palette
import com.poplogic.blipin.ui.theme.Typography
import com.poplogic.blipin.ui.theme.bodyMediumRegular
import com.poplogic.blipin.usecase.connectivity.domain.ConnectivityState
import com.poplogic.blipin.utils.hardcoded
import kotlinx.coroutines.launch
import java.lang.Float.min

@SuppressLint("FrequentlyChangingValue")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExploreScreen(
    contentPaddingValues: PaddingValues,
    viewModel: ExploreViewModel,
    snackbarHostState: SnackbarHostState,
    appNavigationController: NavController,
    scrollState: LazyListState = rememberLazyListState(),
) {
    val connectivityState = viewModel.connectionStatus.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var customSnackbarData by remember<MutableState<LoginSuggestionSnackBarData?>> {
        mutableStateOf(
            null,
        )
    }
    BackHandler(enabled = scrollState.firstVisibleItemScrollOffset > 0 || scrollState.firstVisibleItemIndex > 0) {
        coroutineScope.launch {
            scrollState.animateScrollToItem(0, 0)
        }
    }

    val headerHeightPx = with(LocalDensity.current) { 180.dp.toPx() }

    val scrollRatio by remember {
        derivedStateOf {
            if (scrollState.firstVisibleItemIndex > 0) {
                1f
            } else {
                min(1f, scrollState.firstVisibleItemScrollOffset.toFloat() / headerHeightPx)
            }
        }
    }

    val statusBarTopPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val expandedHeight = 122.dp + statusBarTopPadding
    val collapsedHeight = 80.dp + statusBarTopPadding

    val currentHeight = lerp(start = expandedHeight, stop = collapsedHeight, fraction = scrollRatio)
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Palette.White),
        ) {
            Box(
                modifier =
                    Modifier
                        .height(currentHeight)
                        .fillMaxWidth(),
            ) {
                LargeOrangeHeader(
                    scrollRatio = scrollRatio,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(expandedHeight),
                    onMapButtonTapped = {
                        appNavigationController.navigate(Routes.MAP)
                    },
                )
                CompactStickyHeader(
                    scrollRatio = scrollRatio,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .height(80.dp),
                    onMapButtonTapped = {
                        appNavigationController.navigate(Routes.MAP)
                    },
                )
            }

            LazyColumn(
                state = scrollState,
                modifier = Modifier.fillMaxSize(),
            ) {
                item {
                    Spacer(
                        modifier =
                            Modifier
                                .height(20.dp)
                                .background(Palette.White),
                    )
                }
                item {
                    BrandingSection()
                }
                stickyHeader {
                    FilterChipsSection()
                }
                item {
                    NewStoreSection()
                }
                item {
                    HotSection()
                }
                exploreSection()
                item {
                    Spacer(modifier = Modifier.height(contentPaddingValues.calculateBottomPadding()))
                }
            }
        }

        val shouldShowSnackbar by remember {
            derivedStateOf {
                scrollState.firstVisibleItemIndex >= 3 &&
                    snackbarHostState.currentSnackbarData == null
            }
        }
        LaunchedEffect(
            shouldShowSnackbar,
            connectivityState.value,
        ) {
            when (connectivityState.value) {
                ConnectivityState.DISCONNECTED -> {
                    showNetworkIssueSnackBar(snackbarHostState)
                    customSnackbarData = null
                }

                ConnectivityState.CONNECTED -> {
                    if (shouldShowSnackbar) {
                        val visuals =
                            object : SnackbarVisuals {
                                override val message = "登入享受更多功能"
                                override val actionLabel = "登入/註冊"
                                override val duration = SnackbarDuration.Indefinite
                                override val withDismissAction = false
                            }
                        customSnackbarData =
                            LoginSuggestionSnackBarData(visuals, appNavigationController)
                        snackbarHostState.showSnackbar(
                            message = visuals.message,
                            actionLabel = visuals.actionLabel,
                            duration = visuals.duration,
                        )
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(
                        bottom = contentPaddingValues.calculateBottomPadding() + 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                    ),
            snackbar = { snackbarData ->
                // Use custom snackbar data if available, otherwise use the default
                val dataToUse = customSnackbarData ?: snackbarData
                BottomSnackbar(
                    message = dataToUse.visuals.message,
                    actionLabel = dataToUse.visuals.actionLabel,
                    onActionClick = {
                        dataToUse.performAction()
//                        snackbarData.dismiss()
                    },
                )
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeOrangeHeader(
    scrollRatio: Float,
    modifier: Modifier = Modifier,
    onMapButtonTapped: () -> Unit = {},
) {
    // Inverse alpha: 1.0 at top, 0.0 when fully scrolled
    val alpha = 1f - scrollRatio
    // Parallax effect: moves upward slightly faster than the scroll
    val translationY = -(scrollRatio * 100)

    Box(
        modifier =
            modifier
                .graphicsLayer {
                    this.alpha = alpha
                    this.translationY = translationY
                }.background(Color(0xFFD36125)), // Orange primary theme
    ) {
        ExploreTopBackground(
            modifier =
                Modifier
                    .fillMaxSize(),
            content = {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .statusBarsPadding()
                            .padding(
                                start = 0.dp,
                                bottom = 16.dp,
                            ),
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxHeight()
                                    .wrapContentWidth()
                                    .clickable(onClick = {}),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier =
                                    Modifier
                                        .height(24.dp)
                                        .width(24.dp),
                                painter = painterResource(R.drawable.icon_location),
                                tint = Palette.White,
                                contentDescription = null,
                            )

                            Text(
                                modifier = Modifier.wrapContentWidth(),
                                style =
                                    Typography.bodyLarge.copy(
                                        color = Palette.White,
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.Medium,
                                    ),
                                text = "目前位置".hardcoded(),
                            )
                            Icon(
                                painter = painterResource(R.drawable.arrow_right),
                                contentDescription = null,
                                modifier = Modifier.width(24.dp),
                                tint = Palette.White,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier =
                            Modifier
                                .height(56.dp)
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            val textFieldState = TextFieldState()
                            val onActiveChange = { }
                            // Handle search
                            SearchBar(
                                inputField = {
                                    SearchBarDefaults.InputField(
                                        modifier =
                                            Modifier
                                                .weight(1f)
                                                .border(
                                                    width = 1.dp,
                                                    color = Palette.Neutral.neutral200,
                                                    shape =
                                                        RoundedCornerShape(
                                                            100.dp,
                                                        ),
                                                ),
                                        query = textFieldState.text.toString(),
                                        onQueryChange = {
                                            textFieldState.edit {
                                                replace(0, length, it)
                                            }
                                        },
                                        onSearch = {
                                            // Handle search
                                        },
                                        expanded = false,
                                        onExpandedChange = { onActiveChange() },
                                        placeholder = {
                                            Text(
                                                "搜尋".hardcoded(),
                                                style =
                                                    Typography
                                                        .bodyMediumRegular()
                                                        .copy(color = Palette.Neutral.neutral300),
                                                maxLines = 1,
                                                lineHeight = 20.sp,
                                                textAlign = TextAlign.Start,
                                            )
                                        },
                                        leadingIcon = {
                                            Icon(
                                                modifier =
                                                    Modifier
                                                        .height(20.dp)
                                                        .width(20.dp),
                                                painter = painterResource(R.drawable.icon_search),
                                                contentDescription = null,
                                                tint = Palette.Primary.brand,
                                            )
                                        },
                                        colors =
                                            SearchBarDefaults.inputFieldColors(
                                                focusedLeadingIconColor = Palette.Primary.brand,
                                                unfocusedLeadingIconColor = Palette.Primary.brand,
                                                focusedTrailingIconColor = Palette.Primary.brand,
                                                unfocusedTrailingIconColor = Palette.Primary.brand,
                                                focusedPlaceholderColor = Palette.Neutral.neutral300,
                                                unfocusedPlaceholderColor = Palette.Neutral.neutral300,
                                                focusedTextColor = Palette.Black,
                                                unfocusedTextColor = Palette.Black,
                                                focusedContainerColor = Palette.White,
                                                unfocusedContainerColor = Palette.White,
                                            ),
                                    )
                                },
                                expanded = false,
                                onExpandedChange = { onActiveChange() },
                                modifier =
                                    Modifier
                                        .weight(1f)
                                        .height(56.dp),
                                shape = RoundedCornerShape(100.dp),
                                tonalElevation = SearchBarDefaults.TonalElevation,
                                shadowElevation = SearchBarDefaults.ShadowElevation,
                                windowInsets = WindowInsets(),
                                content = {},
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = {
                                onMapButtonTapped()
                            },
                            modifier =
                                Modifier
                                    .height(48.dp)
                                    .width(48.dp),
                        ) {
                            Icon(
                                modifier =
                                    Modifier
                                        .height(24.dp)
                                        .width(24.dp),
                                painter = painterResource(R.drawable.icon_searchbar),
                                contentDescription = null,
                                tint = Palette.White,
                            )
                        }
                    }
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompactStickyHeader(
    scrollRatio: Float,
    modifier: Modifier = Modifier,
    onMapButtonTapped: () -> Unit = {},
) {
    // Direct alpha: 0.0 at top, 1.0 when scrolled past the threshold
    val alpha = scrollRatio

    if (alpha > 0f) {
        Box(
            modifier =
                modifier
                    .alpha(alpha)
                    .background(Palette.White)
                    .wrapContentHeight(), // Matches the compact light header theme
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize(),
//                                            .wrapContentHeight()
//                                            .heightIn(min = contentPaddingValues.calculateTopPadding() + 78.dp),
                content = {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(
                                    start = 0.dp,
                                    bottom = 16.dp,
                                ),
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier =
                                Modifier
                                    .height(56.dp)
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                modifier =
                                    Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                val textFieldState = TextFieldState()
                                val onActiveChange = { }
                                // Handle search
                                SearchBar(
                                    inputField = {
                                        SearchBarDefaults.InputField(
                                            modifier =
                                                Modifier
                                                    .weight(1f)
                                                    .border(
                                                        width = 1.dp,
                                                        color = Palette.Neutral.neutral200,
                                                        shape =
                                                            RoundedCornerShape(
                                                                100.dp,
                                                            ),
                                                    ),
                                            query = textFieldState.text.toString(),
                                            onQueryChange = {
                                                textFieldState.edit {
                                                    replace(0, length, it)
                                                }
                                            },
                                            onSearch = {
                                                // Handle search
                                            },
                                            expanded = false,
                                            onExpandedChange = { onActiveChange() },
                                            placeholder = {
                                                Text(
                                                    "搜尋".hardcoded(),
                                                    style =
                                                        Typography
                                                            .bodyMediumRegular()
                                                            .copy(color = Palette.Neutral.neutral300),
                                                    maxLines = 1,
                                                    lineHeight = 20.sp,
                                                    textAlign = TextAlign.Start,
                                                )
                                            },
                                            colors =
                                                SearchBarDefaults.inputFieldColors(
                                                    focusedLeadingIconColor = Palette.Primary.brand,
                                                    unfocusedLeadingIconColor = Palette.Primary.brand,
                                                    focusedTrailingIconColor = Palette.Primary.brand,
                                                    unfocusedTrailingIconColor = Palette.Primary.brand,
                                                    focusedPlaceholderColor = Palette.Neutral.neutral300,
                                                    unfocusedPlaceholderColor = Palette.Neutral.neutral300,
                                                    focusedTextColor = Palette.Black,
                                                    unfocusedTextColor = Palette.Black,
                                                    focusedContainerColor = Palette.White,
                                                    unfocusedContainerColor = Palette.White,
                                                ),
                                            leadingIcon = {
                                                Icon(
                                                    modifier =
                                                        Modifier
                                                            .height(20.dp)
                                                            .width(20.dp),
                                                    painter = painterResource(R.drawable.icon_search),
                                                    contentDescription = null,
                                                    tint = Palette.Primary.brand,
                                                )
                                            },
                                        )
                                    },
                                    expanded = false,
                                    onExpandedChange = { onActiveChange() },
                                    modifier =
                                        Modifier
                                            .weight(1f)
                                            .height(56.dp),
                                    shape = RoundedCornerShape(100.dp),
                                    colors =
                                        SearchBarDefaults.colors(
                                            containerColor = Palette.White,
                                        ),
                                    tonalElevation = SearchBarDefaults.TonalElevation,
                                    shadowElevation = SearchBarDefaults.ShadowElevation,
                                    windowInsets = WindowInsets(),
                                    content = {},
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(
                                onClick = {
                                    onMapButtonTapped()
                                },
                                modifier =
                                    Modifier
                                        .height(48.dp)
                                        .width(48.dp),
                            ) {
                                Icon(
                                    modifier =
                                        Modifier
                                            .height(24.dp)
                                            .width(24.dp),
                                    painter = painterResource(R.drawable.icon_searchbar),
                                    contentDescription = null,
                                    tint = Palette.Primary.brand,
                                )
                            }
                        }
                    }
                },
            )
        }
    }
}
