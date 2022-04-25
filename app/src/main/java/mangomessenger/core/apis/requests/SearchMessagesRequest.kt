package mangomessenger.core.apis.requests

import java.util.*

data class SearchMessagesRequest(
    val chatId: UUID,
    val pattern: String
)