package mangomessenger.core.apis.requests

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String,
    val repeatNewPassword: String
)