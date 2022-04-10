package mangomessenger.core.apis

import mangomessenger.core.apis.requests.*
import mangomessenger.core.apis.responses.GetMessagesResponse
import mangomessenger.core.apis.responses.SendMessageResponse
import java.util.concurrent.CompletableFuture

interface MessagesApi {
    fun getMessages(getMessagesRequest: GetMessagesRequest) : CompletableFuture<GetMessagesResponse>

    fun searchMessages(searchMessagesRequest: SearchMessagesRequest) : CompletableFuture<GetMessagesResponse>

    fun sendMessage(request: SendMessageRequest) : CompletableFuture<SendMessageResponse>

    fun editMessage(request: EditMessageRequest) : CompletableFuture<Unit>

    fun deleteMessage(request: DeleteMessageRequest) : CompletableFuture<Unit>
}