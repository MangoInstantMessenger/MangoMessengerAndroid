package com.example.mangomessenger.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mangomessenger.R
import com.example.mangomessenger.ui.registry.RegistryActivity
import com.example.mangomessenger.ui.restore.RestoreActivity
import mangomessenger.bisunesslogic.data.TokenStorageImpl
import mangomessenger.bisunesslogic.services.HttpPipelineFactoryImpl
import mangomessenger.bisunesslogic.services.MangoApisFactoryImpl
import mangomessenger.bisunesslogic.services.SignInServiceImpl

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel

    private lateinit var emailInput: EditText
    private lateinit var emailPrompt: TextView

    private lateinit var passwordInput: EditText
    private lateinit var passwordPrompt: TextView

    private lateinit var loginButton: Button
    private lateinit var createAccountPrompt: TextView
    private lateinit var forgotPasswordPrompt: TextView

    init {
        val tokenStorage = TokenStorageImpl()
        val pipelineFactory = HttpPipelineFactoryImpl(tokenStorage)
        val mangoApisFactory = MangoApisFactoryImpl(pipelineFactory)
        val sessionsApi = mangoApisFactory.createSessionsApi()
        val signInService = SignInServiceImpl(sessionsApi, tokenStorage)
        val loginViewModelFactory = LoginViewModelFactory(signInService)
        loginViewModel = loginViewModelFactory.create(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        initUiComponents()
        addListeners()

        loginViewModel.loginFormState.observe(this) {
            if (it.formWasTouched) {
                if (it.emailError != null) {
                    emailPrompt.text = getString(it.emailError)
                    emailPrompt.visibility = View.VISIBLE
                }
                else {
                    emailPrompt.visibility = View.GONE
                }

                if (it.passwordError != null) {
                    passwordPrompt.text = getString(it.passwordError)
                    passwordPrompt.visibility = View.VISIBLE
                }
                else {
                    passwordPrompt.visibility = View.GONE
                }
            }

            loginButton.isEnabled = it.dataIsValid
        }

        loginViewModel.loginResultState.observe(this) {
            val isEnabled = it.inProgress.not()
            emailInput.isEnabled = isEnabled
            passwordInput.isEnabled = isEnabled
            loginButton.isEnabled = isEnabled

            if (it.loginResponse == null) {
               return@observe
            }

            if (it.loginResponse.success) {
                val msg = "Success: ${it.loginResponse.message}"
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
            else {
                val msg = "Error: ${it.loginResponse.errorMessage}"
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeListeners()
    }

    private fun initUiComponents() {
        emailPrompt = findViewById(R.id.emailPrompt)
        passwordPrompt = findViewById(R.id.passwordPrompt)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.signInButton)
        createAccountPrompt = findViewById(R.id.createAccountPrompt)
        forgotPasswordPrompt = findViewById(R.id.forgotPasswordPrompt)
    }

    private fun addListeners() {
        emailInput.addTextChangedListener(loginViewModel.emailWatcher)
        passwordInput.addTextChangedListener(loginViewModel.passwordWatcher)
        loginButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            loginViewModel.login(email, password)
        }
        createAccountPrompt.setOnClickListener(this::navigateToRegistry)
        forgotPasswordPrompt.setOnClickListener(this::navigateToRestore)
    }

    private fun removeListeners() {
        emailInput.removeTextChangedListener(loginViewModel.emailWatcher)
        passwordInput.removeTextChangedListener(loginViewModel.passwordWatcher)
        loginButton.setOnClickListener(null)
        createAccountPrompt.setOnClickListener(null)
        forgotPasswordPrompt.setOnClickListener(null)
    }

    private fun navigateToRegistry(view: View) {
        val registryActivity = Intent(this, RegistryActivity::class.java)
        startActivity(registryActivity)
    }

    private fun navigateToRestore(view: View) {
        val restoreActivity = Intent(this, RestoreActivity::class.java)
        startActivity(restoreActivity)
    }
}