package com.poplogic.blipin.presentation.explore.presentation.sections

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.loading.LoadingCardWithDescription
import com.poplogic.blipin.presentation.explore.presentation.composables.verticalSection

fun LazyListScope.exploreSection() {
    verticalSection(
        title = "探索".hardcoded(),
        items = 5,
        itemContent = {
            LoadingCardWithDescription(
                height = 248.dp,
                borderRadius = 8.dp,
            )
        },
    )
}
