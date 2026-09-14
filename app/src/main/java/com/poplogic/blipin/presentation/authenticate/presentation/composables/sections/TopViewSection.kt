package com.poplogic.blipin.presentation.authenticate.presentation.composables.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poplogic.blipin.R
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.feature.common.theme.Typography

@Composable
internal fun TopViewSection(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .wrapContentSize(),
    ) {
        Image(
            painter = painterResource(R.drawable.blipin_word),
            contentDescription = "Blipin Logo",
            contentScale = ContentScale.FillWidth,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "輕鬆探索你附近的餐車".hardcoded(),
            style =
                Typography.bodyLarge.copy(
                    color = Palette.Primary.brand,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                ),
        )
    }
}
