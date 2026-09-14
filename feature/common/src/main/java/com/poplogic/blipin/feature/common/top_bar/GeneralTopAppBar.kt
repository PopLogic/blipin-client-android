package com.poplogic.blipin.feature.common.top_bar

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.poplogic.blipin.feature.common.R
import com.poplogic.blipin.feature.common.theme.Palette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralTopAppBar(
    title: String,
    onBack: (() -> Unit)?,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = Palette.Black,
            )
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = Palette.White,
            ),
        navigationIcon = {
            onBack?.let {
                IconButton(onClick = onBack) {
                    Icon(
                        painter =
                            painterResource(id = R.drawable.icon_navigation_back_large),
                        contentDescription = "Back",
                        tint = Palette.Black,
                    )
                }
            }
        },
    )
}
