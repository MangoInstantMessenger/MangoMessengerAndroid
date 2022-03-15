package mangomessenger.core.apis.utils

import mangomessenger.core.apis.responses.BaseResponse

sealed class HttpResponseUtils private constructor() {
    companion object {
        fun unauthenticatedResponse(): BaseResponse = BaseResponse().apply {
            statusCode = 401
            success = false
            errorMessage = "Unauthenticated"
            errorDetails = "Unauthenticated"
        }
    }
}
