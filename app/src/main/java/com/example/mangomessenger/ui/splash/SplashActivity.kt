package com.example.mangomessenger.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mangomessenger.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
    }
}