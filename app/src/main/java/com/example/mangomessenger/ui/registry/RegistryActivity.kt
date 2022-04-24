package com.example.mangomessenger.ui.registry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.mangomessenger.R
import com.example.mangomessenger.ui.login.LoginActivity
import com.example.mangomessenger.ui.restore.RestoreActivity

class RegistryActivity : AppCompatActivity() {
    private val registryViewModel: RegistryViewModel

    private lateinit var displayNameInput: EditText
    private lateinit var displayNamePrompt: TextView

    private lateinit var emailInput: EditText
    private lateinit var emailPrompt: TextView

    private lateinit var passwordInput: EditText
    private lateinit var passwordPrompt: TextView

    private lateinit var confirmPasswordInput: EditText
    private lateinit var confirmPasswordPrompt: TextView

    private lateinit var registryButton: Button
    private lateinit var loginPrompt: TextView
    private lateinit var forgotPasswordPrompt: TextView

    init {
        val registryViewModelFactory = RegistryViewModelFactory()
        registryViewModel = registryViewModelFactory.create(RegistryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry)
        supportActionBar?.hide()
        initUiComponents()
        addListeners()

        registryViewModel.registryFormState.observe(this) {
            if (it.displayNameError != null && it.displayNameTouched) {
                displayNamePrompt.text = getString(it.displayNameError)
                displayNamePrompt.visibility = View.VISIBLE
            }
            else {
                displayNamePrompt.visibility = View.GONE
            }

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

            if (it.confirmPasswordError != null && it.confirmPasswordTouched) {
                confirmPasswordPrompt.text = getString(it.confirmPasswordError)
                confirmPasswordPrompt.visibility = View.VISIBLE
            }
            else {
                confirmPasswordPrompt.visibility = View.GONE
            }

            registryButton.isEnabled = it.dataIsValid
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeListeners()
    }

    private fun initUiComponents() {
        displayNameInput = findViewById(R.id.displayNameInput)
        displayNamePrompt = findViewById(R.id.displayNamePrompt)
        emailInput = findViewById(R.id.emailInput)
        emailPrompt = findViewById(R.id.emailPrompt)
        passwordInput = findViewById(R.id.passwordInput)
        passwordPrompt = findViewById(R.id.passwordPrompt)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        confirmPasswordPrompt = findViewById(R.id.confirmPasswordPrompt)
        registryButton = findViewById(R.id.registryButton)
        loginPrompt = findViewById(R.id.backToLoginPrompt)
        forgotPasswordPrompt = findViewById(R.id.forgotPasswordPrompt)
    }

    private fun addListeners() {
        displayNameInput.addTextChangedListener(registryViewModel.displayNameWatcher)
        emailInput.addTextChangedListener(registryViewModel.emailWatcher)
        passwordInput.addTextChangedListener(registryViewModel.passwordWatcher)
        confirmPasswordInput.addTextChangedListener(registryViewModel.confirmPasswordWatcher)
        loginPrompt.setOnClickListener(this::navigateToLogin)
        forgotPasswordPrompt.setOnClickListener(this::navigateToRestore)
    }

    private fun removeListeners() {
        displayNameInput.removeTextChangedListener(registryViewModel.displayNameWatcher)
        emailInput.removeTextChangedListener(registryViewModel.emailWatcher)
        passwordInput.removeTextChangedListener(registryViewModel.passwordWatcher)
        confirmPasswordInput.removeTextChangedListener(registryViewModel.confirmPasswordWatcher)
        loginPrompt.setOnClickListener(null)
        forgotPasswordPrompt.setOnClickListener(null)
    }

    private fun navigateToLogin(view: View) {
        val activity = Intent(this, LoginActivity::class.java)
        startActivity(activity)
    }

    private fun navigateToRestore(view: View) {
        val activity = Intent(this, RestoreActivity::class.java)
        startActivity(activity)
    }
}