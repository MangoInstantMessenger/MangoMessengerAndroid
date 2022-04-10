package mangomessenger.core.apis.responses

import mangomessenger.core.types.models.Message

data class GetMessagesResponse(
    val messages: ArrayList<Message>
    ) : BaseResponse()