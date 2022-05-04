package com.example.mangomessenger.ui.registry

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mangomessenger.R
import com.example.mangomessenger.databinding.FragmentRegistryBinding
import com.example.mangomessenger.ui.login.LoginActivity
import com.example.mangomessenger.ui.restore.RestoreActivity

class RegistryFragment : Fragment() {

    private val registryViewModel: RegistryViewModel
    private var _binding: FragmentRegistryBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = RegistryFragment()
    }

    init {
        val factory = RegistryViewModelFactory()
        registryViewModel = factory.create(RegistryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistryBinding.inflate(inflater, container, false)
        val view = binding.root
        addListeners()

        registryViewModel.registryFormState.observe(this.viewLifecycleOwner) {
            val displayNamePrompt = binding.displayNamePrompt
            val emailPrompt = binding.emailPrompt
            val passwordPrompt = binding.passwordPrompt
            val confirmPasswordPrompt = binding.confirmPasswordPrompt

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

            binding.registryButton.isEnabled = it.dataIsValid
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeListeners()
        _binding = null
    }

    private fun addListeners() {
        binding.displayNameInput.addTextChangedListener(registryViewModel.displayNameWatcher)
        binding.emailInput.addTextChangedListener(registryViewModel.emailWatcher)
        binding.passwordInput.addTextChangedListener(registryViewModel.passwordWatcher)
        binding.confirmPasswordInput.addTextChangedListener(registryViewModel.confirmPasswordWatcher)
        binding.registryButton.setOnClickListener {
            if (this.isAdded) {
                val afterRegistryFragment = AfterRegistryFragment.newInstance()
                val transaction = parentFragmentManager.beginTransaction();
                transaction.replace(R.id.registry_fragment_container_view, afterRegistryFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        binding.backToLoginPrompt.setOnClickListener(this::navigateToLogin)
        binding.forgotPasswordPrompt.setOnClickListener(this::navigateToRestore)
    }

    private fun removeListeners() {
        binding.displayNameInput.removeTextChangedListener(registryViewModel.displayNameWatcher)
        binding.emailInput.removeTextChangedListener(registryViewModel.emailWatcher)
        binding.passwordInput.removeTextChangedListener(registryViewModel.passwordWatcher)
        binding.confirmPasswordInput.removeTextChangedListener(registryViewModel.confirmPasswordWatcher)
        binding.registryButton.setOnClickListener(null)
        binding.backToLoginPrompt.setOnClickListener(null)
        binding.forgotPasswordPrompt.setOnClickListener(null)
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