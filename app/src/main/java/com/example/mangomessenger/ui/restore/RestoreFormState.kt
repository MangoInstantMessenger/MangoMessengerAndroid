package com.example.mangomessenger.ui.restore

data class RestoreFormState(
    val emailError: Int? = null,
    val emailTouched: Boolean = false,
    val dataIsValid: Boolean = false
)