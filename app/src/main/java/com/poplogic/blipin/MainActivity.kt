package com.poplogic.blipin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.poplogic.blipin.feature.onboard.presentation.OnboardPageScreen
import com.poplogic.blipin.feature.onboard.presentation.viewmodel.OnboardViewModel
import com.poplogic.blipin.ui.theme.BlipinTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: OnboardViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlipinTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Vertical),
                ) { innerPadding ->
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(
//                                top = innerPadding.calculateTopPadding(),
//                                bottom = innerPadding.calculateBottomPadding(),
                            ),
                    ) {
//                        Spacer(
//                            modifier =
//                                Modifier
//                                    .height(innerPadding.calculateTopPadding())
//                                    .background(color = Color(0xFFFF6600)),
//                        )
                        OnboardPageScreen(
                            modifier =
                                Modifier.padding(
                                    top = innerPadding.calculateTopPadding(),
                                    bottom =
                                        innerPadding.calculateBottomPadding(),
                                ),
                            viewModel = viewModel,
                        )
//                        Spacer(
//                            modifier =
//                                Modifier
//                                    .height(innerPadding.calculateBottomPadding())
//                                    .background(color = Color.White),
//                        )
                    }
                }
            }
        }
    }
}
