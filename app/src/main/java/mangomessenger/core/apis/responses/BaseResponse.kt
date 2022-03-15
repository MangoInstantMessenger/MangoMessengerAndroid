package mangomessenger.core.apis.responses

open class BaseResponse {
    var success: Boolean = false
    var message: String? = null
    var statusCode: Int? = null
    var errorMessage: String? = null
    var errorDetails: String? = null
}