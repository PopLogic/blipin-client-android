package com.poplogic.blipin.feature.explore.presentation.sections

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.common_ui.loading.LoadingCardWithDescription
import com.poplogic.blipin.feature.explore.presentation.composables.verticalSection
import com.poplogic.blipin.utils.hardcoded

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
