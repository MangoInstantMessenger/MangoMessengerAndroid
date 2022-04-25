package mangomessenger.core.apis

import com.google.gson.Gson
import mangomessenger.core.apis.requests.*
import mangomessenger.core.apis.responses.DeleteMessageResponse
import mangomessenger.core.apis.responses.EditMessageResponse
import mangomessenger.core.apis.responses.GetMessagesResponse
import mangomessenger.core.apis.responses.SendMessageResponse
import mangomessenger.http.HttpMethods
import mangomessenger.http.HttpRequest
import mangomessenger.http.JsonContent
import mangomessenger.http.declarations.applyJsonContent
import mangomessenger.http.pipelines.HttpPipeline
import java.net.URLEncoder
import java.util.concurrent.CompletableFuture

class MessagesApiImpl(
    private val domain: String,
    private val httpPipeline: HttpPipeline
) : MessagesApi {

    private val gson = Gson()

    override fun getMessages(getMessagesRequest: GetMessagesRequest): CompletableFuture<GetMessagesResponse> {
        val url = "$domain/api/messages/${getMessagesRequest.chatId}"
        val request = HttpRequest(HttpMethods.GET, url)
        val response = httpPipeline.handleRequest(request)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), GetMessagesResponse::class.java)
        }
    }

    override fun searchMessages(searchMessagesRequest: SearchMessagesRequest): CompletableFuture<GetMessagesResponse> {
        val url = "$domain/api/messages/searches/${searchMessagesRequest.chatId}"
        val request = HttpRequest(HttpMethods.GET, url).apply {
            queryParameters["messageText"] = searchMessagesRequest.pattern
        }
        val response = httpPipeline.handleRequest(request)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), GetMessagesResponse::class.java)
        }
    }

    override fun sendMessage(sendMessageRequest: SendMessageRequest): CompletableFuture<SendMessageResponse> {
        val url = "$domain/api/messages"
        val content = JsonContent(gson.toJson(sendMessageRequest))
        val request = HttpRequest(HttpMethods.POST, url).applyJsonContent(content)
        val response = httpPipeline.handleRequest(request)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), SendMessageResponse::class.java)
        }
    }

    override fun editMessage(editMessageRequest: EditMessageRequest): CompletableFuture<EditMessageResponse> {
        val url = "$domain/api/messages"
        val content = JsonContent(gson.toJson(editMessageRequest))
        val request = HttpRequest(HttpMethods.PUT, url).applyJsonContent(content)
        val response = httpPipeline.handleRequest(request)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), EditMessageResponse::class.java)
        }
    }

    override fun deleteMessage(deleteMessageRequest: DeleteMessageRequest): CompletableFuture<DeleteMessageResponse> {
        val url = "$domain/api/messages"
        val content = JsonContent(gson.toJson(deleteMessageRequest))
        val request = HttpRequest(HttpMethods.DELETE, url).applyJsonContent(content)
        val response = httpPipeline.handleRequest(request)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), DeleteMessageResponse::class.java)
        }
    }
}