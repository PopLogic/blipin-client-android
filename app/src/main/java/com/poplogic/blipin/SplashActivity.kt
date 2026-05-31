package com.poplogic.blipin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat.Type

class SplashActivity : AppCompatActivity() {
    private val handler: Handler = Handler(Looper.getMainLooper())
    private val runnable: Runnable =
        Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        val root: View = findViewById(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val navBarBottom = insets.getInsets(Type.navigationBars()).bottom
            v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, navBarBottom)
            insets
        }
        ViewCompat.requestApplyInsets(root)
        handler.postDelayed(runnable, 5000)
    }

    @SuppressLint("ImplicitSamInstance")
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks { runnable }
    }
}
