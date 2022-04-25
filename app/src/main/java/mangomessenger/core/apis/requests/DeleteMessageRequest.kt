package mangomessenger.core.apis.requests

import java.util.*

data class DeleteMessageRequest(
    val messageId: UUID,
    val chatId: UUID
)