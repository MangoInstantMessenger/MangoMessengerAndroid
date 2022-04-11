package com.example.mangomessenger

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.mangomessenger.viewmodels.SignInViewModel
import com.example.mangomessenger.viewmodels.SignInViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import mangomessenger.bisunesslogic.data.TokenStorageImpl
import mangomessenger.bisunesslogic.services.HttpPipelineFactoryImpl
import mangomessenger.bisunesslogic.services.MangoApisFactoryImpl
import mangomessenger.bisunesslogic.services.SignInServiceImpl

class SignInActivity : AppCompatActivity() {
    private val signInViewModel: SignInViewModel

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: MaterialButton

    init {
        val tokenStorage = TokenStorageImpl()
        val pipelineFactory = HttpPipelineFactoryImpl(tokenStorage)
        val mangoApisFactory = MangoApisFactoryImpl(pipelineFactory)
        val sessionsApi = mangoApisFactory.createSessionsApi()
        val signInService = SignInServiceImpl(sessionsApi, tokenStorage)
        val signInViewModelFactory = SignInViewModelFactory(signInService)
        signInViewModel = signInViewModelFactory.create(SignInViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportActionBar?.hide()

        initUiComponents()

        signInViewModel.loginResult.observe(this) { loginResult ->
            if (!loginResult.success) {
                Snackbar.make(emailEditText.rootView,
                    loginResult.errorMessage.orEmpty(),
                    Snackbar.LENGTH_SHORT)
                    .show()
            }

            else {
                // TODO: Redirect to MainActivity.
            }
        }

        signInViewModel.loginModel.observe(this) { loginModel ->
            if (loginModel.emailHelp.isNotEmpty()) {
                // TODO: Show help with email.
            }
            else {
                // TODO: Remove help with email.
            }

            if (loginModel.passwordHelp.isNotEmpty()) {
                // TODO: Show help with password.
            }
            else {
                // TODO: Remove help with password.
            }

            if (loginModel.waiting) {
                // TODO: Show waiting animation and lock edits.
            }
            else {
                // TODO: Remove waiting animation and unlock edits.
            }
        }

        addListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        removeListeners()
    }

    private fun initUiComponents() {
        emailEditText = findViewById(R.id.textInputEmailAddress)
        passwordEditText = findViewById(R.id.textInputPassword)
        signInButton = findViewById(R.id.MaterialButtonLogin)
    }

    private fun addListeners() {
        emailEditText.addTextChangedListener(signInViewModel.emailTextWatcher)
        passwordEditText.addTextChangedListener(signInViewModel.passwordTextWatcher)
        signInButton.setOnClickListener(signInViewModel::onSignIn)
    }

    private fun removeListeners() {
        emailEditText.removeTextChangedListener(signInViewModel.emailTextWatcher)
        passwordEditText.removeTextChangedListener(signInViewModel.passwordTextWatcher)
        signInButton.setOnClickListener(null)
    }
}