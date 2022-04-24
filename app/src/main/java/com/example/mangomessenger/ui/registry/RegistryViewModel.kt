package com.example.mangomessenger.ui.registry

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mangomessenger.R

class RegistryViewModel : ViewModel() {
    private val _registryFormState = MutableLiveData(RegistryFormState(
        displayNameError = R.string.displayNameErrorEmpty,
        emailError = R.string.emailErrorFormat,
        passwordError = R.string.passwordErrorRequirements,
        confirmPasswordError = R.string.confirmPasswordError
    ))
    val registryFormState: LiveData<RegistryFormState> get() = _registryFormState

    private val _registryResultState = MutableLiveData(RegistryResultState())
    val registryResultState: LiveData<RegistryResultState> get() = _registryResultState

    private val _registryForm = MutableLiveData(RegistryForm())
    val registryForm: LiveData<RegistryForm> get() = _registryForm

    val displayNameWatcher = object : TextWatcher {
        override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            _registryForm.value = _registryForm.value?.copy(displayName = text.toString())
            validateDisplayName(_registryForm.value?.displayName.orEmpty())
        }

        override fun afterTextChanged(text: Editable?) {
            if (_registryFormState.value?.displayNameTouched != true) {
                _registryFormState.value = _registryFormState.value?.copy(
                    displayNameTouched = true
                )
            }

            validateForm()
        }
    }

    val emailWatcher = object : TextWatcher {
        override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            _registryForm.value = _registryForm.value?.copy(email = text.toString())
            validateEmail(registryForm.value?.email.orEmpty())
        }

        override fun afterTextChanged(text: Editable?) {
            if (_registryFormState.value?.emailTouched != true) {
                _registryFormState.value = _registryFormState.value?.copy(
                    emailTouched = true
                )
            }

            validateForm()
        }
    }

    val passwordWatcher = object : TextWatcher {
        override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            _registryForm.value = _registryForm.value?.copy(password = text.toString())
            val password = _registryForm.value?.password.orEmpty()
            val confirmPassword = _registryForm.value?.confirmPassword.orEmpty()
            validatePassword(password)
            validatePasswordConfirmation(password, confirmPassword)
        }

        override fun afterTextChanged(text: Editable?) {
            if (_registryFormState.value?.passwordTouched != true) {
                _registryFormState.value = _registryFormState.value?.copy(
                    passwordTouched = true
                )
            }

            validateForm()
        }

    }

    val confirmPasswordWatcher = object : TextWatcher {
        override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            _registryForm.value = _registryForm.value?.copy(confirmPassword = text.toString())
            val password = _registryForm.value?.password.orEmpty()
            val confirmPassword = _registryForm.value?.confirmPassword.orEmpty()
            validatePasswordConfirmation(password, confirmPassword)
        }

        override fun afterTextChanged(text: Editable?) {
            if (_registryFormState.value?.confirmPasswordTouched != true) {
                _registryFormState.value = _registryFormState.value?.copy(
                    confirmPasswordTouched = true
                )
            }

            validateForm()
        }

    }

    private fun dataIsValid() : Boolean {
        return _registryFormState.value?.displayNameError == null &&
                _registryFormState.value?.emailError == null &&
                _registryFormState.value?.passwordError == null &&
                _registryFormState.value?.confirmPasswordError == null
    }

    private fun validateForm() {
        _registryFormState.value = _registryFormState.value?.copy(
            dataIsValid = dataIsValid()
        )
    }

    private fun validateDisplayName(displayName: String) {
        if (displayName.isEmpty()) {
            _registryFormState.value = _registryFormState.value?.copy(
                displayNameError = R.string.displayNameErrorEmpty
            )
        }
        else {
            _registryFormState.value = _registryFormState.value?.copy(
                displayNameError = null
            )
        }
    }

    private fun validateEmail(email: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _registryFormState.value = _registryFormState.value?.copy(
                emailError = R.string.emailErrorFormat
            )
        }
        else {
            _registryFormState.value = _registryFormState.value?.copy(
                emailError = null
            )
        }
    }

    private fun validatePassword(password: String) {
        val strongPasswordLiteral = "(?=^.{8,}\$)(?=.*\\d)(?=.*[!@#\$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*\$"
        val pattern = Regex.fromLiteral(strongPasswordLiteral)
        if (pattern.matches(password).not()) {
            _registryFormState.value = _registryFormState.value?.copy(
                passwordError = R.string.passwordErrorRequirements
            )
        }
        else {
            _registryFormState.value = _registryFormState.value?.copy(
                passwordError = null
            )
        }
    }

    private fun validatePasswordConfirmation(password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            _registryFormState.value = _registryFormState.value?.copy(
                confirmPasswordError = R.string.confirmPasswordError
            )
        }
        else {
            _registryFormState.value = _registryFormState.value?.copy(
                confirmPasswordError = null
            )
        }
    }
}