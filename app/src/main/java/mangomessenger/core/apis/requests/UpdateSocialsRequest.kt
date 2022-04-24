package mangomessenger.core.apis.requests

data class UpdateSocialsRequest(
    val facebook: String,
    val twitter: String,
    val instagram: String,
    val linkedIn: String
)
