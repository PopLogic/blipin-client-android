package com.poplogic.blipin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.poplogic.blipin.nav.AppNavigation
import com.poplogic.blipin.ui.theme.BlipinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)

        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        enableEdgeToEdge()
        setContent {
            BlipinTheme {
                AppNavigation()
            }
        }
    }
}
