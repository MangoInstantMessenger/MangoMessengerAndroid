package mangomessenger.core.types.models

import java.util.*

data class Tokens(
    val accessToken: String,
    val refreshToken: UUID,
    val userId: UUID,
    val expires: Int)