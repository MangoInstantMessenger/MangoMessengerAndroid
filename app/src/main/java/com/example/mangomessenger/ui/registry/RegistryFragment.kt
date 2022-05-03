package com.example.mangomessenger.ui.registry

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mangomessenger.R
import com.example.mangomessenger.ui.login.LoginActivity
import com.example.mangomessenger.ui.restore.RestoreActivity

class RegistryFragment : Fragment() {

    companion object {
        fun newInstance() = RegistryFragment()
    }

    private lateinit var registryViewModel: RegistryViewModel
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registry, container, false)
        initUiComponents(view)
        addListeners()
        registryViewModel.registryFormState.observe(this.viewLifecycleOwner) {
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
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registryViewModel = RegistryViewModelFactory().create(RegistryViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeListeners()
    }

    private fun initUiComponents(view: View) {
        displayNameInput = view.findViewById(R.id.displayNameInput)
        displayNamePrompt = view.findViewById(R.id.displayNamePrompt)
        emailInput = view.findViewById(R.id.emailInput)
        emailPrompt = view.findViewById(R.id.emailPrompt)
        passwordInput = view.findViewById(R.id.passwordInput)
        passwordPrompt = view.findViewById(R.id.passwordPrompt)
        confirmPasswordInput = view.findViewById(R.id.confirmPasswordInput)
        confirmPasswordPrompt = view.findViewById(R.id.confirmPasswordPrompt)
        registryButton = view.findViewById(R.id.registryButton)
        loginPrompt = view.findViewById(R.id.backToLoginPrompt)
        forgotPasswordPrompt = view.findViewById(R.id.forgotPasswordPrompt)
    }

    private fun addListeners() {
        displayNameInput.addTextChangedListener(registryViewModel.displayNameWatcher)
        emailInput.addTextChangedListener(registryViewModel.emailWatcher)
        passwordInput.addTextChangedListener(registryViewModel.passwordWatcher)
        confirmPasswordInput.addTextChangedListener(registryViewModel.confirmPasswordWatcher)
        registryButton.setOnClickListener {
            if (this.isAdded) {
                val afterRegistryFragment = AfterRegistryFragment.newInstance()
                val transaction = parentFragmentManager.beginTransaction();
                transaction.replace(R.id.registry_fragment_container_view, afterRegistryFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        loginPrompt.setOnClickListener(this::navigateToLogin)
        forgotPasswordPrompt.setOnClickListener(this::navigateToRestore)
    }

    private fun removeListeners() {
        displayNameInput.removeTextChangedListener(registryViewModel.displayNameWatcher)
        emailInput.removeTextChangedListener(registryViewModel.emailWatcher)
        passwordInput.removeTextChangedListener(registryViewModel.passwordWatcher)
        confirmPasswordInput.removeTextChangedListener(registryViewModel.confirmPasswordWatcher)
        registryButton.setOnClickListener(null)
        loginPrompt.setOnClickListener(null)
        forgotPasswordPrompt.setOnClickListener(null)
    }

    private fun navigateToLogin(view: View) {
        val activity = Intent(requireContext(), LoginActivity::class.java)
        startActivity(activity)
    }

    private fun navigateToRestore(view: View) {
        val activity = Intent(requireContext(), RestoreActivity::class.java)
        startActivity(activity)
    }
}