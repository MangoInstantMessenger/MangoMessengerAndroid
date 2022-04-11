package com.example.mangomessenger.viewmodels

import android.net.MailTo
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mangomessenger.models.LoginModel
import mangomessenger.bisunesslogic.services.SignInService
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.apis.responses.LoginResponse

class SignInViewModel(private val signInService: SignInService) : ViewModel() {
    private val _loginModel = MutableLiveData<LoginModel>()
    val loginModel: LiveData<LoginModel> get() = _loginModel

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> get() = _loginResult

    init {
        _loginModel.value = LoginModel()
        signInService.isSigned().thenAccept {
            _loginResult.postValue(it)
        }
    }

    val emailTextWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString()
                val emailHelp = if (MailTo.isMailTo(email)) "" else "Invalid Email format."
                _loginModel.value = loginModel.value?.copy(
                    email = email,
                    emailHelp = emailHelp)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }

    val passwordTextWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                val passwordHelp = if (s.isNullOrEmpty()) "Password is empty." else ""
                _loginModel.value = _loginModel.value?.copy(
                    password = password,
                    passwordHelp = passwordHelp)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        }

    fun onSignIn(v: View) {
        val loginRequest = LoginRequest(loginModel.value!!.email, loginModel.value!!.password)
        signInService.signIn(loginRequest).thenAccept {
            _loginResult.postValue(it)
        }
    }
}