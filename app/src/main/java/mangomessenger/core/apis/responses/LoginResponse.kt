package mangomessenger.core.apis.responses

import mangomessenger.core.types.models.Tokens

data class LoginResponse(
    val tokens: Tokens?
) : BaseResponse()
