package com.example.mangomessenger.ui.restore

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mangomessenger.R

class RestoreViewModel : ViewModel() {
    private val _restoreFormState = MutableLiveData(RestoreFormState())
    val restoreFormState: LiveData<RestoreFormState> get() = _restoreFormState

    private val _restoreForm = MutableLiveData(RestoreForm())
    val restoreForm: LiveData<RestoreForm> get() = _restoreForm

    val emailWatcher = object : TextWatcher {
        override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val email = text.toString()
            validateEmail(email)
        }

        override fun afterTextChanged(text: Editable?) {
            if (_restoreFormState.value?.emailTouched != true) {
                _restoreFormState.value = _restoreFormState.value?.copy(emailTouched = true)
            }

            validateForm()
        }
    }

    fun sendRestoreRequest() {

    }

    private fun validateEmail(email: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _restoreFormState.value = _restoreFormState.value?.copy(
                emailError = R.string.emailErrorFormat
            )
        }
        else {
            _restoreFormState.value = _restoreFormState.value?.copy(
                emailError = null
            )
        }
    }

    private fun validateForm() {
        val dataIsValid = _restoreFormState.value?.emailError == null
        _restoreFormState.value = _restoreFormState.value?.copy(
            dataIsValid = dataIsValid
        )
    }
}