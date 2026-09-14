package com.poplogic.blipin.presentation.explore.presentation.sections

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.presentation.explore.presentation.composables.Section

@Composable
fun HotSection() {
    Section(
        title = "熱門店家".hardcoded(),
        onSeeAllClick = { /* Handle click */ },
        modifier =
            Modifier
                .height(318.dp)
                .fillMaxWidth(),
    )
}
