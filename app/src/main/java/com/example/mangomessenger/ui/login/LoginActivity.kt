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
import com.example.mangomessenger.databinding.ActivityLoginBinding
import com.example.mangomessenger.ui.registry.RegistryActivity
import com.example.mangomessenger.ui.restore.RestoreActivity
import mangomessenger.bisunesslogic.data.TokenStorageProvider
import mangomessenger.bisunesslogic.services.HttpPipelineFactoryImpl
import mangomessenger.bisunesslogic.services.MangoApisFactoryImpl
import mangomessenger.bisunesslogic.services.SignInServiceImpl

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    init {
        val tokenStorage = TokenStorageProvider.getTokenStorage()
        val pipelineFactory = HttpPipelineFactoryImpl(tokenStorage)
        val mangoApisFactory = MangoApisFactoryImpl(pipelineFactory)
        val sessionsApi = mangoApisFactory.createSessionsApi()
        val signInService = SignInServiceImpl(sessionsApi, tokenStorage)
        val loginViewModelFactory = LoginViewModelFactory(signInService)
        loginViewModel = loginViewModelFactory.create(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        addListeners()

        loginViewModel.loginFormState.observe(this) {
            val emailPrompt = binding.emailPrompt
            val passwordPrompt = binding.passwordPrompt

            if (it.emailError != null && it.emailTouched) {
                emailPrompt.text = getString(it.emailError)
                emailPrompt.visibility = View.VISIBLE
            }
            else {
                emailPrompt.visibility = View.GONE
            }

            if (it.passwordError != null && it.passwordTouched) {
                passwordPrompt.text = getString(it.passwordError)
                passwordPrompt.visibility = View.VISIBLE
            }
            else {
                passwordPrompt.visibility = View.GONE
            }

            binding.signInButton.isEnabled = it.dataIsValid
        }

        loginViewModel.loginResultState.observe(this) {
            val isEnabled = it.inProgress.not()
            binding.emailInput.isEnabled = isEnabled
            binding.passwordInput.isEnabled = isEnabled
            binding.signInButton.isEnabled = isEnabled

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

    private fun addListeners() {
        binding.emailInput.addTextChangedListener(loginViewModel.emailWatcher)
        binding.passwordInput.addTextChangedListener(loginViewModel.passwordWatcher)
        binding.signInButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            loginViewModel.login(email, password)
        }
        binding.createAccountPrompt.setOnClickListener(this::navigateToRegistry)
        binding.forgotPasswordPrompt.setOnClickListener(this::navigateToRestore)
    }

    private fun removeListeners() {
        binding.emailInput.removeTextChangedListener(loginViewModel.emailWatcher)
        binding.passwordInput.removeTextChangedListener(loginViewModel.passwordWatcher)
        binding.signInButton.setOnClickListener(null)
        binding.createAccountPrompt.setOnClickListener(null)
        binding.forgotPasswordPrompt.setOnClickListener(null)
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