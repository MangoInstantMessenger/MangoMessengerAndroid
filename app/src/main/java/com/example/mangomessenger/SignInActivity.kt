package com.example.mangomessenger

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.mangomessenger.viewmodels.SignInViewModel
import com.example.mangomessenger.viewmodels.SignInViewModelFactory
import com.google.android.material.button.MaterialButton
import mangomessenger.bisunesslogic.data.TokenStorageImpl
import mangomessenger.bisunesslogic.services.HttpPipelineFactoryImpl
import mangomessenger.bisunesslogic.services.MangoApisFactoryImpl
import mangomessenger.bisunesslogic.services.SignInServiceImpl

class SignInActivity : AppCompatActivity() {
    private lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportActionBar?.hide()

        val tokenStorage = TokenStorageImpl()
        val pipelineFactory = HttpPipelineFactoryImpl(tokenStorage)
        val mangoApisFactory = MangoApisFactoryImpl(pipelineFactory)
        val sessionsApi = mangoApisFactory.createSessionsApi()
        val signInService = SignInServiceImpl(sessionsApi, tokenStorage)
        val signInViewModelFactory = SignInViewModelFactory(signInService)
        signInViewModel = signInViewModelFactory.create(SignInViewModel::class.java)
        signInViewModel.loginResult.observe(this) { loginResult ->
            val message = loginResult.message ?: loginResult.errorMessage
            println(message)
            // TODO: Redirect to MainActivity if response is success
        }
        val emailTextBox = findViewById<EditText>(R.id.textInputEmailAddress)
        emailTextBox.addTextChangedListener(signInViewModel.emailTextWatcher)
        val passwordTextBox = findViewById<EditText>(R.id.textInputPassword)
        passwordTextBox.addTextChangedListener(signInViewModel.passwordTextWatcher)
        val button = findViewById<MaterialButton>(R.id.MaterialButtonLogin)
        button.setOnClickListener(signInViewModel::onSignInClicked)
    }
}