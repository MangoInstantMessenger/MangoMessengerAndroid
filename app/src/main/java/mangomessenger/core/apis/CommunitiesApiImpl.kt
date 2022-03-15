package mangomessenger.core.apis

import com.google.gson.Gson
import mangomessenger.core.apis.requests.CreateCommunityRequest
import mangomessenger.core.apis.responses.CreateCommunityResponse
import mangomessenger.core.apis.responses.GetChatsResponse
import mangomessenger.core.apis.responses.UpdateChannelPictureResponse
import mangomessenger.core.apis.utils.safetyHttpRequestAsync
import mangomessenger.http.*
import mangomessenger.http.declarations.applyMultipartFormContent
import java.io.File
import java.util.*
import java.util.concurrent.CompletableFuture

class CommunitiesApiImpl(private val httpClient: HttpClient) : CommunitiesApi {
    private val gson = Gson()
    private val domain = "https://back.mangomessenger.company"

    override fun getChatsAsync(): CompletableFuture<GetChatsResponse> {
        TODO("Not yet implemented")
    }

    override fun createChannelAsync(request: CreateCommunityRequest): CompletableFuture<CreateCommunityResponse> {
        TODO("Not yet implemented")
    }

    override fun createChatWithUserAsync(userId: UUID): CompletableFuture<CreateCommunityResponse> {
        TODO("Not yet implemented")
    }

    override fun searchChatsAsync(displayName: String): CompletableFuture<GetChatsResponse> {
        TODO("Not yet implemented")
    }

    override fun uploadChannelPictureAsync(
        chatId: UUID,
        picture: File
    ): CompletableFuture<UpdateChannelPictureResponse> {
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
        val response = httpClient.safetyHttpRequestAsync(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), UpdateChannelPictureResponse::class.java)
        }
    }
}