package mangomessenger.core.apis

import java.util.concurrent.CompletableFuture

interface CommunitiesApi {
    /**
     * Get all user's chats.
     */
    fun getChatsAsync(request: Any): CompletableFuture<Any>

    /**
     * Create new group of specified type: Public Channel (2).
     */
    fun createChannelAsync(request: Any): CompletableFuture<Any>

    /**
     * Creates new chat with specified user with type of: Direct Chat (1).
     * If chat already exists: returns its ID.
     */
    fun createChatWithUserAsync(request: Any): CompletableFuture<Any>

    /**
     * Searches chats by display name.
     */
    fun searchChatsAsync(request: Any): CompletableFuture<Any>

    /**
     * Updates picture of particular channel.
     */
    fun uploadChannelPictureAsync(request: Any): CompletableFuture<Any>
}