package com.erik.jetpackpro.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import com.erik.jetpackpro.R
import com.erik.jetpackpro.ui.main.MainActivity

class SplashScreen : AppCompatActivity() {
    lateinit var handler: Handler

    companion object {
        const val Delay = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, Delay.toLong())
    }
}