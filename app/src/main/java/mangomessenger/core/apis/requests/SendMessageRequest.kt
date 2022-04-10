package mangomessenger.core.apis.requests

import java.util.*

data class SendMessageRequest(
    val messageText: String,
    val chatId: UUID,
    val attachmentUrl: String?,
    val inReplayToAuthor: String?,
    val inReplayToText: String?
)