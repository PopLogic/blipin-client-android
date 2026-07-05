package com.poplogic.blipin.feature.explore.presentation.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.common_ui.loading.LoadingCard
import com.poplogic.blipin.ui.theme.Palette

@Composable
fun BrandingSection() {
    LazyRow(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(188.dp)
                .padding(vertical = 4.dp)
                .background(color = Palette.White),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
    ) {
        items(3) { index ->
            LoadingCard(
                width = 343.dp,
                borderRadius = 4.dp,
            )
            if (index < 2) {
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}
