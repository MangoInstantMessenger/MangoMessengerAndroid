package mangomessenger.core.apis

import com.google.gson.Gson
import mangomessenger.core.apis.requests.CreateCommunityRequest
import mangomessenger.core.apis.responses.CreateCommunityResponse
import mangomessenger.core.apis.responses.GetChatsResponse
import mangomessenger.core.apis.responses.UpdateChannelPictureResponse
import mangomessenger.http.*
import mangomessenger.http.declarations.applyJsonContent
import mangomessenger.http.declarations.applyMultipartFormContent
import mangomessenger.http.pipelines.HttpPipeline
import java.io.File
import java.util.*
import java.util.concurrent.CompletableFuture

class CommunitiesApiImpl(
    private val domain: String,
    private val httpPipeline: HttpPipeline
    ) : CommunitiesApi {

    private val gson = Gson()

    override fun getChatsAsync(): CompletableFuture<GetChatsResponse> {
        val url = "$domain/api/communities"
        val httpRequest = HttpRequest(HttpMethods.GET, url)
        val response = httpPipeline.handleRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), GetChatsResponse::class.java)
        }
    }

    override fun createChannelAsync(request: CreateCommunityRequest): CompletableFuture<CreateCommunityResponse> {
        val url = "$domain/api/communities/channel"
        val content = JsonContent(gson.toJson(request))
        val httpRequest = HttpRequest(HttpMethods.POST, url).applyJsonContent(content)
        val response = httpPipeline.handleRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), CreateCommunityResponse::class.java)
        }
    }

    override fun createChatWithUserAsync(userId: UUID): CompletableFuture<CreateCommunityResponse> {
        val url = "$domain/api/communities/chat/$userId"
        val httpRequest = HttpRequest(HttpMethods.POST, url, JsonContent())
        val response = httpPipeline.handleRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), CreateCommunityResponse::class.java)
        }
    }

    override fun searchChatsAsync(displayName: String): CompletableFuture<GetChatsResponse> {
        val url = "$domain/api/communities/searches"
        val httpRequest = HttpRequest(HttpMethods.GET, url).apply {
            queryParameters["displayName"] = displayName
        }
        val response = httpPipeline.handleRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), GetChatsResponse::class.java)
        }
    }

    override fun uploadChannelPictureAsync(
        chatId: UUID,
        picture: File
    ): CompletableFuture<UpdateChannelPictureResponse> {
        println("picturePath: ${picture.absolutePath}")
        println("length: ${picture.length()}")
        println("extensions: ${picture.extension}")
        println("isFile: ${picture.isFile}")
        println("name: ${picture.name}")
        val multipartForm = MultipartFormContent().apply {
            formFields.add(MultipartFormFile(
                name = "newGroupPicture",
                fileName = picture.name,
                contentType = "image/${picture.extension}",
                picture
            ))
        }
        val url = "$domain/api/communities/picture/$chatId"
        val httpRequest = HttpRequest(HttpMethods.POST, url).applyMultipartFormContent(multipartForm)
        val response = httpPipeline.handleRequest(httpRequest)
        return response.thenApply {
            println("uploadResponseCode: ${it.connection.responseCode}")
            return@thenApply gson.fromJson(String(it.data), UpdateChannelPictureResponse::class.java)
        }
    }
}