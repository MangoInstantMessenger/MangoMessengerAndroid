package mangomessenger.core.apis.requests

data class UpdateAccountRequest(
    val displayName: String,
    val birthdayDate: String,
    val website: String,
    val username: String,
    val bio: String,
    val address: String
)
