package com.poplogic.blipin.feature.explore.presentation.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.feature.explore.domain.FoodType
import com.poplogic.blipin.feature.explore.presentation.composables.FilterChipItem
import com.poplogic.blipin.ui.theme.Palette

@Composable
fun FilterChipsSection(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
) {
    LazyRow(
        modifier =
            modifier
                .height(48.dp)
                .background(color = backgroundColor)
                .padding(vertical = 7.dp)
                .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(FoodType.entries.size) { index ->
            val foodType = FoodType.entries[index]
            FilterChipItem(foodType = foodType)
        }
    }
}

@Preview
@Composable
fun FilterChipsSectionPreview() {
    FilterChipsSection()
}
