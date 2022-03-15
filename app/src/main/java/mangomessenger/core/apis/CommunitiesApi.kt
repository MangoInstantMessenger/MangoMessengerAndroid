package mangomessenger.core.apis

import mangomessenger.core.apis.requests.CreateCommunityRequest
import mangomessenger.core.apis.responses.CreateCommunityResponse
import mangomessenger.core.apis.responses.GetChatsResponse
import mangomessenger.core.apis.responses.UpdateChannelPictureResponse
import java.io.File
import java.util.*
import java.util.concurrent.CompletableFuture

interface CommunitiesApi {
    /**
     * Get all user's chats.
     */
    fun getChatsAsync(): CompletableFuture<GetChatsResponse>

    /**
     * Create new group of specified type: Public Channel (2).
     */
    fun createChannelAsync(request: CreateCommunityRequest): CompletableFuture<CreateCommunityResponse>

    /**
     * Creates new chat with specified user with type of: Direct Chat (1).
     * If chat already exists: returns its ID.
     */
    fun createChatWithUserAsync(userId: UUID): CompletableFuture<CreateCommunityResponse>

    /**
     * Searches chats by display name.
     */
    fun searchChatsAsync(displayName: String): CompletableFuture<GetChatsResponse>

    /**
     * Updates picture of particular channel.
     */
    fun uploadChannelPictureAsync(chatId: UUID, picture: File): CompletableFuture<UpdateChannelPictureResponse>
}