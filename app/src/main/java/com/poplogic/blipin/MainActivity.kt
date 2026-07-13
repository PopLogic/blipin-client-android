package com.poplogic.blipin

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.compose.rememberNavController
import com.poplogic.blipin.nav.AppNavigation
import com.poplogic.blipin.ui.theme.BlipinTheme

class MainActivity : ComponentActivity() {
    val requestPermissionsLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            androidx.activity.result.contract.ActivityResultContracts
                .RequestMultiplePermissions(),
        ) { permissions ->
            // Handle the result of the permission request
            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted = it.value
                if (isGranted) {
                    // Permission granted, you can perform the related action
                } else {
                    // Permission denied, you can show a message or handle accordingly
                }
            }
        }

    val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            androidx.activity.result.contract.ActivityResultContracts
                .RequestPermission(),
        ) { isGranted ->
            if (isGranted) {
                // Permission granted, you can perform the related action
            } else {
                // Permission denied, you can show a message or handle accordingly
            }
        }

    fun requestLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected, and what
                // features are disabled if it's declined. In this UI, include a
                // "cancel" or "no thanks" button that lets the user continue
                // using your app without granting the permission.
                // Show an educational UI to explain the permission request
            }

            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fun hideSystemBars() {
            val windowInsetsController =
                WindowCompat.getInsetsController(window, window.decorView)
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        }

        fun showSystemBars() {
            val windowInsetsController =
                WindowCompat.getInsetsController(window, window.decorView)
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        }

        enableEdgeToEdge()
        setContent {
            val appNavigationController = rememberNavController()
            BlipinTheme {
                AppNavigation(
                    hideSystemBars = { hideSystemBars() },
                    showSystemBars = { showSystemBars() },
                    appNavigationController = appNavigationController,
                )
            }
        }
    }
}
