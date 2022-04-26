package com.example.mangomessenger.ui.login

data class LoginFormState(
    val emailError: Int? = null,
    val emailTouched: Boolean = false,
    val passwordError: Int? = null,
    val passwordTouched: Boolean = false,
    val dataIsValid: Boolean = false
)