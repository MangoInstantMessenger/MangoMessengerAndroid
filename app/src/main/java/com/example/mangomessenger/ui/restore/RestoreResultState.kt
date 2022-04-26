package com.example.mangomessenger.ui.restore

import mangomessenger.core.apis.responses.BaseResponse

data class RestoreResultState(
    val isProgress: Boolean = false,
    val restorePasswordResponse: BaseResponse? = null
)