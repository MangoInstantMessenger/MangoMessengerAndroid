package com.example.mangomessenger.ui.login

import mangomessenger.core.apis.responses.LoginResponse

data class LoginResultState(
    val inProgress: Boolean = false,
    val loginResponse: LoginResponse? = null
)