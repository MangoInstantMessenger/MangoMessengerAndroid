package com.example.mangomessenger

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import mangomessenger.core.apis.SessionsApiImpl
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.http.*
import mangomessenger.http.pipelines.HttpPipeline

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportActionBar?.hide()
    }


    fun login(view: View) {
        val emailInput: TextInputEditText = findViewById(R.id.textInputEmailAddress)
        val passwordInput: TextInputEditText = findViewById(R.id.textInputPassword)
        val loginRequest = LoginRequest(emailInput.text.toString(), passwordInput.text.toString())
        val sessionsApi = SessionsApiImpl(HttpPipeline())
        val requestAsync = sessionsApi.loginAsync(loginRequest)
        val response = requestAsync.get()
        val message = response.message ?: response.errorMessage ?: ""
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}