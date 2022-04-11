package com.example.mangomessenger.models

data class LoginModel(
    val email: String = "",
    val emailHelp: String = "",
    val password: String = "",
    val passwordHelp: String = "",
    val waiting: Boolean = false)