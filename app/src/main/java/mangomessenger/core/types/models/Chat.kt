package mangomessenger.core.types.models

import mangomessenger.core.types.enums.CommunityType
import java.util.*

data class Chat(
    var chatId: UUID,
    var title: String,
    var communityType: CommunityType,
    var chatLogoImageUrl: String,
    var description: String,
    var membersCount: Int,
    var isArchived: Boolean,
    var isMember: Boolean,
    var roleId: Int,
    var lastMessageAuthor: String,
    var lastMessageText: String,
    var lastMessageTime: String,
    var lastMessageId: String,
    var updatedAt: String
)
