package mangomessenger.core.apis.responses

import mangomessenger.core.types.models.User

data class GetUserResponse(val user: User) : BaseResponse()
