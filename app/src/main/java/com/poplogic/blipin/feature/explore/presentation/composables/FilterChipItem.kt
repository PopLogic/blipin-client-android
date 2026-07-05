package com.poplogic.blipin.feature.explore.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poplogic.blipin.feature.explore.domain.FoodType
import com.poplogic.blipin.ui.theme.Palette
import com.poplogic.blipin.ui.theme.Typography

@Composable
fun FilterChipItem(
    modifier: Modifier = Modifier,
    foodType: FoodType,
    isSelected: Boolean = false,
) {
    Row(
        modifier =
            modifier
                .fillMaxHeight()
                .wrapContentWidth()
                .clip(RoundedCornerShape(100.dp))
                .border(
                    width = 1.dp,
                    color = Palette.Neutral.neutral100,
                    shape = RoundedCornerShape(100.dp),
                ).background(color = Palette.White)
                .clickable(onClick = { /* Handle click */ })
                .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(foodType.iconRes),
            contentDescription = foodType.name,
            modifier = Modifier.size(32.dp),
            tint = foodType.color,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = foodType.name,
            style =
                Typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = Palette.Neutral.neutralBrand,
                ),
        )
    }
}

@Preview
@Composable
fun FilterChipItemPreview() {
    FilterChipItem(
        modifier = Modifier.height(48.dp),
        foodType = FoodType.entries[0],
        isSelected = false,
    )
}
