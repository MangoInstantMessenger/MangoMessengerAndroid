package com.example.mangomessenger.ui.login

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mangomessenger.R
import mangomessenger.bisunesslogic.services.SignInService
import mangomessenger.core.apis.requests.LoginRequest
import java.util.concurrent.CompletableFuture

class LoginViewModel(private val signInService: SignInService) : ViewModel() {
    private val _loginFormState = MutableLiveData(LoginFormState(
        emailError = R.string.emailErrorFormat,
        passwordError = R.string.passwordErrorEmpty
    ))
    val loginFormState: LiveData<LoginFormState> get() = _loginFormState

    private val _loginResult = MutableLiveData(LoginResultState())
    val loginResultState: LiveData<LoginResultState> get() = _loginResult

    val emailWatcher = object : TextWatcher {
        override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val email = text.toString()
            validateEmail(email)
        }

        override fun afterTextChanged(text: Editable?) {
            if (_loginFormState.value?.emailTouched != true) {
                _loginFormState.value = _loginFormState.value?.copy(emailTouched = true)
            }

            validateForm()
        }
    }

    val passwordWatcher = object : TextWatcher {
        override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val password = text.toString()
            validatePassword(password)
        }

        override fun afterTextChanged(text: Editable?) {
            if (_loginFormState.value?.passwordTouched != true) {
                _loginFormState.value = _loginFormState.value?.copy(passwordTouched = true)
            }

            validateForm()
        }
    }

    private fun validateForm() {
        _loginFormState.value = _loginFormState.value?.copy(dataIsValid = dataIsValid())
    }

    private fun validatePassword(password: String) {
        if (password.isEmpty()) {
            _loginFormState.value = _loginFormState.value?.copy(passwordError = R.string.passwordErrorEmpty)
        }
        else {
            _loginFormState.value = _loginFormState.value?.copy(passwordError = null)
        }
    }

    private fun validateEmail(email: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _loginFormState.value = _loginFormState.value?.copy(emailError = R.string.emailErrorFormat)
        }
        else {
            _loginFormState.value = _loginFormState.value?.copy(emailError = null)
        }
    }

    private fun dataIsValid(): Boolean {
        return _loginFormState.value?.emailError == null && _loginFormState.value?.passwordError == null
    }

    fun login(email: String, password: String): CompletableFuture<Void> {
        validateEmail(email)
        validatePassword(password)
        if (!dataIsValid()) {
            return CompletableFuture()
        }

        return CompletableFuture
            .supplyAsync {
                _loginResult.postValue(LoginResultState(inProgress = true))
            }
            .thenCompose {
                val loginRequest = LoginRequest(email, password)
                return@thenCompose signInService.signIn(loginRequest)
            }
            .thenAcceptAsync {
                _loginResult.postValue(
                    LoginResultState(
                        inProgress = false,
                        loginResponse = it
                    )
                )
            }
    }
}