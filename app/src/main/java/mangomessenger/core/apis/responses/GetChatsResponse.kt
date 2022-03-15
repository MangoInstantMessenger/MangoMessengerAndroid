package mangomessenger.core.apis.responses

import mangomessenger.core.types.models.Chat

data class GetChatsResponse(val chats: Collection<Chat>) : BaseResponse()