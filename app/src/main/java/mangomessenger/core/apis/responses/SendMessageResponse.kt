package mangomessenger.core.apis.responses

import java.util.*

data class SendMessageResponse(
    val messageId: UUID
    ) : BaseResponse()