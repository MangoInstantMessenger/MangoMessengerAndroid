package mangomessenger.core.types.models

import java.util.*

data class User(
    val userId: UUID,
    val displayName: String,
    val birthDate: String,
    val email: String,
    val website: String,
    val username: String,
    val bio: String,
    val address: String,
    val facebook: String,
    val twitter: String,
    val instagram: String,
    val linkedIn: String,
    val publicKey: Int,
    val pictureUrl: String
)
