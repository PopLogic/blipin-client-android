package com.poplogic.blipin.common_ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(
    url: String,
    screenTitle: String,
    onBack: () -> Unit,
) {
    BackHandler {
        onBack()
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(screenTitle) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { padding ->
        AndroidView(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = false
                    settings.domStorageEnabled = true
                    webViewClient = object : WebViewClient() {}
                    loadUrl(url)
                }
            },
            update = { webView ->
                if (webView.url != url) webView.loadUrl(url)
            },
        )
    }
}
