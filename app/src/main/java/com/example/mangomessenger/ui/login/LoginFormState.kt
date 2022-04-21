package com.example.mangomessenger.ui.login

data class LoginFormState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val dataIsValid: Boolean = false,
    val formWasTouched: Boolean = false
)