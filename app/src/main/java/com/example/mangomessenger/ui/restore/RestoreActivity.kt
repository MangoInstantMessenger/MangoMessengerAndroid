package com.example.mangomessenger.ui.restore

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mangomessenger.databinding.ActivityRestoreBinding
import com.example.mangomessenger.ui.login.LoginActivity
import com.example.mangomessenger.ui.registry.RegistryActivity

class RestoreActivity : AppCompatActivity() {
    private val restoreViewModel: RestoreViewModel
    private lateinit var binding: ActivityRestoreBinding

    init {
        val restoreViewModelFactory = RestoreViewModelFactory()
        restoreViewModel = restoreViewModelFactory.create(RestoreViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        addListeners()

        restoreViewModel.restoreFormState.observe(this) {
            val emailPrompt = binding.emailPrompt
            binding.requestRestorePasswordButton.isEnabled = it.dataIsValid

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

    private fun addListeners() {
        binding.emailInput.addTextChangedListener(restoreViewModel.emailWatcher)
        binding.requestRestorePasswordButton.setOnClickListener { restoreViewModel.sendRestoreRequest() }
        binding.backToLoginPrompt.setOnClickListener(this::navigateToLogin)
        binding.createAccountPrompt.setOnClickListener(this::navigateToRegistry)
    }

    private fun removeListeners() {
        binding.emailInput.removeTextChangedListener(restoreViewModel.emailWatcher)
        binding.requestRestorePasswordButton.setOnClickListener(null)
        binding.backToLoginPrompt.setOnClickListener(null)
        binding.createAccountPrompt.setOnClickListener(null)
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