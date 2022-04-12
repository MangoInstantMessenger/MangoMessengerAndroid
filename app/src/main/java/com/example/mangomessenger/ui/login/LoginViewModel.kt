package com.example.mangomessenger.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mangomessenger.bisunesslogic.services.SignInService

class LoginViewModel(private val signInService: SignInService) : ViewModel() {
    private val _loginForm = MutableLiveData(LoginFormState())
    val loginFormState: LiveData<LoginFormState> get() = _loginForm

    private val _loginResult = MutableLiveData(LoginResultState())
    val loginResultState: LiveData<LoginResultState> get() = _loginResult


}