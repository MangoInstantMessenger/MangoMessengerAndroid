package com.example.mangomessenger.ui.registry

data class RegistryForm(
    val displayName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)