package mangomessenger.core.apis.requests

import java.util.*

data class EditMessageRequest(
    val messageId: UUID,
    val modifiedText: String,
    val chatId: UUID
)