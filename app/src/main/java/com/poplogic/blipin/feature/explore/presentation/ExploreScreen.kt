package com.poplogic.blipin.feature.explore.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.poplogic.blipin.R
import com.poplogic.blipin.common_ui.background.ExploreTopBackground
import com.poplogic.blipin.common_ui.snack_bar.showNetworkIssueSnackBar
import com.poplogic.blipin.feature.explore.presentation.sections.BrandingSection
import com.poplogic.blipin.feature.explore.presentation.sections.FilterChipsSection
import com.poplogic.blipin.feature.explore.presentation.sections.HotSection
import com.poplogic.blipin.feature.explore.presentation.sections.NewStoreSection
import com.poplogic.blipin.feature.explore.presentation.sections.exploreSection
import com.poplogic.blipin.ui.theme.Palette
import com.poplogic.blipin.ui.theme.Typography
import com.poplogic.blipin.ui.theme.bodyMediumRegular
import com.poplogic.blipin.usecase.connectivity.ConnectivityState
import com.poplogic.blipin.utils.hardcoded
import java.lang.Float.min

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExploreScreen(
    contentPaddingValues: PaddingValues,
    viewModel: ExploreViewModel,
    snackbarHostState: SnackbarHostState,
) {
    val connectivityState = viewModel.connectionStatus.collectAsState()
    val scrollState = rememberLazyListState()

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
                )
                CompactStickyHeader(
                    scrollRatio = scrollRatio,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .height(80.dp),
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
        LaunchedEffect(connectivityState.value) {
            when (connectivityState.value) {
                ConnectivityState.DISCONNECTED -> {
                    if (snackbarHostState.currentSnackbarData == null) {
                        showNetworkIssueSnackBar(snackbarHostState)
                    }
                }

                ConnectivityState.CONNECTED -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
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
                Row(
                    modifier =
                        Modifier
                            .dropShadow(
                                shape = RoundedCornerShape(8.dp),
                                shadow =
                                    Shadow(
                                        radius = 10.dp,
                                        spread = 8.dp,
                                        color = Palette.Black.copy(alpha = 0.1f),
                                    ),
                            ).clip(RoundedCornerShape(8.dp))
                            .background(Palette.White)
                            .wrapContentHeight()
                            .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = snackbarData.visuals.message,
                        modifier =
                            Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .wrapContentWidth(),
                        style =
                            Typography.bodyMediumRegular().copy(
                                color = Palette.Black,
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                            ),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = { snackbarData.dismiss() },
                        modifier =
                            Modifier
                                .wrapContentWidth(),
                    ) {
                        Text(
                            text = "關閉".hardcoded(),
                            style =
                                Typography.bodyMediumRegular().copy(
                                    color = Palette.Primary.brand,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                ),
                        )
                    }
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeOrangeHeader(
    scrollRatio: Float,
    modifier: Modifier = Modifier,
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
                                                focusedPlaceholderColor = Palette.Neutral.neutral300,
                                                unfocusedPlaceholderColor = Palette.Neutral.neutral300,
                                                focusedTextColor = Palette.Black,
                                                unfocusedTextColor = Palette.Black,
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
                                // Handle search icon click
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
                                                    focusedPlaceholderColor = Palette.Neutral.neutral300,
                                                    unfocusedPlaceholderColor = Palette.Neutral.neutral300,
                                                    focusedTextColor = Palette.Black,
                                                    unfocusedTextColor = Palette.Black,
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
                                    // Handle search icon click
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

@Composable
fun FakeSearchBar(
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(backgroundColor, RoundedCornerShape(24.dp))
                .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "搜尋...", color = textColor, fontSize = 14.sp)
        Spacer(modifier = Modifier.weight(1f))
        // Settings/Filter icon placeholder
        Text(text = "⌥", color = Color.Gray, fontSize = 18.sp)
    }
}
