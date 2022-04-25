package mangomessenger.core.types.models

import java.util.*

data class Message(
    val messageId: UUID,
    val chatId: UUID,
    val userId: UUID,
    val userDisplayName: String,
    val messageText: String,
    val createdAt: String,
    val updatedAt: String,
    val self: Boolean,
    val messageAuthorPictureUrl: String?,
    val messageAttachmentUrl: String?,
    val inReplayToAuthor: String?,
    val inReplayToText: String?
)
