package com.poplogic.blipin.feature.authenticate.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.R
import com.poplogic.blipin.common_ui.textfield.SingleLineTextField
import com.poplogic.blipin.feature.authenticate.presentation.composables.TopView
import com.poplogic.blipin.ui.theme.Palette
import com.poplogic.blipin.utils.hardcoded

@Composable
fun AuthenticationScreen(modifier: Modifier = Modifier) {
    val textFieldState = remember { mutableStateOf("") }
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(
                    color = Palette.SplashBackground,
                ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopView()
        Spacer(Modifier.height(57.dp))
        SingleLineTextField(
            modifier =
                Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
            placeHolder = "請輸入電子信箱".hardcoded(),
            value = textFieldState.value,
            onValueChange = { textFieldState.value = it },
            trailingIcon = R.drawable.icon_clean,
        )
    }
}
