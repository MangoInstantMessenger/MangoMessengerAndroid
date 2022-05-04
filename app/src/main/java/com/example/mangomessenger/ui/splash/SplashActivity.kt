package com.example.mangomessenger.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mangomessenger.R
import com.example.mangomessenger.ui.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CompletableFuture

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
    }

    override fun onStart() {
        super.onStart()
        CompletableFuture.runAsync {
            val loginPage = Intent(this, LoginActivity::class.java)
            runBlocking {
                delay(3000)
                startActivity(loginPage)
                finish()
            }
        }
    }
}