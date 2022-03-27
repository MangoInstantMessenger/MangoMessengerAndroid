package mangomessenger.core.types.models

import java.util.*

data class Tokens(
    val accessToken: String = "",
    val refreshToken: UUID = UUID(0L, 0L),
    val userId: UUID = UUID(0L, 0L),
    val expires: Int = -1)