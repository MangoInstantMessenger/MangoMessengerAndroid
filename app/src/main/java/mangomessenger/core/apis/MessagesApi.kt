package mangomessenger.core.apis

import mangomessenger.core.apis.requests.*
import mangomessenger.core.apis.responses.DeleteMessageResponse
import mangomessenger.core.apis.responses.EditMessageResponse
import mangomessenger.core.apis.responses.GetMessagesResponse
import mangomessenger.core.apis.responses.SendMessageResponse
import java.util.concurrent.CompletableFuture

interface MessagesApi {
    fun getMessages(getMessagesRequest: GetMessagesRequest) : CompletableFuture<GetMessagesResponse>

    fun searchMessages(searchMessagesRequest: SearchMessagesRequest) : CompletableFuture<GetMessagesResponse>

    fun sendMessage(sendMessageRequest: SendMessageRequest) : CompletableFuture<SendMessageResponse>

    fun editMessage(editMessageRequest: EditMessageRequest) : CompletableFuture<EditMessageResponse>

    fun deleteMessage(deleteMessageRequest: DeleteMessageRequest) : CompletableFuture<DeleteMessageResponse>
}