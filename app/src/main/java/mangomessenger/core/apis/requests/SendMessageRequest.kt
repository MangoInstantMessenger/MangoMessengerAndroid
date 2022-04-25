package mangomessenger.core.apis.requests

import java.util.*

data class SendMessageRequest(
    val messageText: String,
    val chatId: UUID,
    val attachmentUrl: String? = null,
    val inReplayToAuthor: String? = null,
    val inReplayToText: String? = null
)