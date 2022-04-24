package mangomessenger.core.apis.requests

data class RegisterRequest(
    val displayName: String,
    val email: String,
    val password: String
)