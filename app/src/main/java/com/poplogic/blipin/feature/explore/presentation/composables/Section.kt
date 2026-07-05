package com.poplogic.blipin.feature.explore.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poplogic.blipin.common_ui.button.SeeAllButton
import com.poplogic.blipin.common_ui.loading.LoadingCardWithDescription
import com.poplogic.blipin.ui.theme.Typography

@Composable
fun Section(
    modifier: Modifier = Modifier,
    title: String,
    onSeeAllClick: (() -> Unit)? = null,
    isRowBased: Boolean = true,
) {
    Column(modifier = modifier.padding(top = 12.dp, bottom = 8.dp)) {
        SectionHeader(title = title, onSeeAllClick = onSeeAllClick)
        SectionBody(isRowBased = isRowBased)
    }
}

@Composable
fun SectionHeader(
    title: String,
    onSeeAllClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            style =
                Typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    lineHeight = 28.sp,
                ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        // Add a "See All" button or clickable text here
        if (onSeeAllClick != null) {
            SeeAllButton(onClick = onSeeAllClick)
        }
    }
}

@Composable
fun SectionBody(
    modifier: Modifier = Modifier,
    isRowBased: Boolean = true,
) {
    if (isRowBased) {
        SectionBodyRow(modifier = modifier)
    } else {
        SectionBodyColumn(modifier = modifier)
    }
}

fun <T> LazyListScope.horizontalSection(
    title: String,
    items: List<T>,
    key: (T) -> Any,
    onSeeAllClick: (() -> Unit)? = null,
    keyPrefix: String = title,
    itemContent: @Composable (T) -> Unit,
) {
    item(key = "${keyPrefix}_header") {
        SectionHeader(
            title = title,
            onSeeAllClick = onSeeAllClick,
            modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
        )
    }

    item(key = "${keyPrefix}_body") {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(
                items = items,
                key = key,
            ) { model ->
                Column(
                    modifier = Modifier.padding(vertical = 6.dp),
                ) {
                    itemContent(model)
                }
            }
        }
    }
}

fun LazyListScope.verticalSection(
    title: String,
//    items: List<T>,
    items: Int,
//    key: (T) -> Any,
    onSeeAllClick: (() -> Unit)? = null,
    keyPrefix: String = title,
    itemContent: @Composable () -> Unit,
) {
    item(key = "${keyPrefix}_header") {
        SectionHeader(
            title = title,
            onSeeAllClick = onSeeAllClick,
            modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
        )
    }

    items(
        count = items,
    ) { index ->
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
        ) {
            itemContent()
        }
    }
}

@Composable
private fun SectionBodyRow(modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier.padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        items(3) { index ->
            LoadingCardWithDescription(
                width = 280.dp,
                height = 250.dp,
                borderRadius = 8.dp,
            )
        }
    }
}

@Composable
fun SectionBodyColumn(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier =
            modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        items(5) { index ->
            LoadingCardWithDescription(
                height = 248.dp,
                borderRadius = 8.dp,
            )
        }
    }
}
