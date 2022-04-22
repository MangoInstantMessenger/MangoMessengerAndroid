package com.example.mangomessenger.ui.registry

data class RegistryFormState(
    val displayNameError: Int? = null,
    val displayNameWasTouched: Boolean = false,
    val emailError: Int? = null,
    val emailWasTouched: Boolean = false,
    val passwordError: Int? = null,
    val passwordWasTouched: Boolean = false,
    val confirmPasswordError: Int? = null,
    val confirmPasswordWasTouched: Boolean = false,
    val dataIsValid: Boolean = false,
)