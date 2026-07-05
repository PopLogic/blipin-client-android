package com.poplogic.blipin.feature.explore.presentation.sections

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.feature.explore.presentation.composables.Section
import com.poplogic.blipin.utils.hardcoded

@Composable
fun NewStoreSection() {
    Section(
        title = "新店家".hardcoded(),
        onSeeAllClick = { /* Handle click */ },
        modifier =
            Modifier
                .height(318.dp)
                .fillMaxWidth(),
    )
}
