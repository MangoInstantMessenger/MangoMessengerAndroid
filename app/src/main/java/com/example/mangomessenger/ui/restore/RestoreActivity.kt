package com.example.mangomessenger.ui.restore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.mangomessenger.R
import com.example.mangomessenger.ui.login.LoginActivity
import com.example.mangomessenger.ui.registry.RegistryActivity

class RestoreActivity : AppCompatActivity() {
    private val restoreViewModel: RestoreViewModel

    private lateinit var emailInput: EditText
    private lateinit var emailPrompt: TextView
    private lateinit var requestRestorePasswordButton: Button
    private lateinit var backToLoginPrompt: TextView
    private lateinit var createAccountPrompt: TextView

    init {
        val restoreViewModelFactory = RestoreViewModelFactory()
        restoreViewModel = restoreViewModelFactory.create(RestoreViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore)
        supportActionBar?.hide()
        initUiComponents()
        addListeners()

        restoreViewModel.restoreFormState.observe(this) {
            requestRestorePasswordButton.isEnabled = it.dataIsValid

            if (it.emailError != null && it.emailTouched) {
                emailPrompt.text = getString(it.emailError)
                emailPrompt.visibility = View.VISIBLE
            }
            else {
                emailPrompt.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeListeners()
    }

    private fun initUiComponents() {
        emailInput = findViewById(R.id.emailInput)
        emailPrompt = findViewById(R.id.emailPrompt)
        requestRestorePasswordButton = findViewById(R.id.requestRestorePasswordButton)
        backToLoginPrompt = findViewById(R.id.backToLoginPrompt)
        createAccountPrompt = findViewById(R.id.createAccountPrompt)
    }

    private fun addListeners() {
        emailInput.addTextChangedListener(restoreViewModel.emailWatcher)
        requestRestorePasswordButton.setOnClickListener { restoreViewModel.sendRestoreRequest() }
        backToLoginPrompt.setOnClickListener(this::navigateToLogin)
        createAccountPrompt.setOnClickListener(this::navigateToRegistry)
    }

    private fun removeListeners() {
        emailInput.removeTextChangedListener(restoreViewModel.emailWatcher)
        requestRestorePasswordButton.setOnClickListener(null)
        backToLoginPrompt.setOnClickListener(null)
        createAccountPrompt.setOnClickListener(null)
    }

    private fun navigateToLogin(view: View) {
        val activity = Intent(this, LoginActivity::class.java)
        startActivity(activity)
    }

    private fun navigateToRegistry(view: View) {
        val activity = Intent(this, RegistryActivity::class.java)
        startActivity(activity)
    }
}