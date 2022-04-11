package com.example.mangomessenger.viewmodels

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mangomessenger.models.SignInModel
import com.google.android.material.snackbar.Snackbar
import mangomessenger.bisunesslogic.services.SignInService
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.apis.responses.LoginResponse

class SignInViewModel(private val signInService: SignInService) : ViewModel() {
    private val _signInModel = MutableLiveData<SignInModel>()
    val signInModel: LiveData<SignInModel> get() = _signInModel

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> get() = _loginResult

    init {
        _signInModel.value = SignInModel("", "")
        signInService.isSigned().thenAccept {
            _loginResult.postValue(it)
        }
    }

    val emailTextWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                _signInModel.postValue(_signInModel.value?.copy(email = s.toString()))
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        }

    val passwordTextWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                _signInModel.value = _signInModel.value?.copy(password = s.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        }

    fun onSignInClicked(v: View) {
        val email = _signInModel.value?.email.orEmpty()
        val password = _signInModel.value?.password.orEmpty()
        val loginRequest = LoginRequest(email, password)
        signInService.signIn(loginRequest).thenAccept {
            _loginResult.postValue(it)
            val message = it.message ?: it.errorMessage
            Snackbar.make(v, message.orEmpty(), Snackbar.LENGTH_SHORT).show()
        }
    }
}