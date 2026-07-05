package com.poplogic.blipin.common_ui.background

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.poplogic.blipin.R

@Composable
fun ExploreTopBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) = Box(
    modifier =
        modifier
            .fillMaxSize(),
) {
    Image(
        painter = painterResource(R.drawable.explore_top_background),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth,
    )

    content()
}
