package com.example.mangomessenger.ui.registry

data class RegistryFormState(
    val displayNameError: Int? = null,
    val displayNameTouched: Boolean = false,
    val emailError: Int? = null,
    val emailTouched: Boolean = false,
    val passwordError: Int? = null,
    val passwordTouched: Boolean = false,
    val confirmPasswordError: Int? = null,
    val confirmPasswordTouched: Boolean = false,
    val dataIsValid: Boolean = false,
)