package mangomessenger.core.apis

import com.google.gson.Gson
import mangomessenger.core.apis.requests.ChangePasswordRequest
import mangomessenger.core.apis.requests.RegisterRequest
import mangomessenger.core.apis.requests.UpdateAccountRequest
import mangomessenger.core.apis.requests.UpdateSocialsRequest
import mangomessenger.core.apis.responses.BaseResponse
import mangomessenger.core.apis.responses.GetUserResponse
import mangomessenger.core.apis.responses.UpdateProfilePictureResponse
import mangomessenger.http.*
import mangomessenger.http.declarations.applyJsonContent
import mangomessenger.http.declarations.applyMultipartFormContent
import mangomessenger.http.pipelines.HttpPipeline
import java.io.File
import java.util.*
import java.util.concurrent.CompletableFuture

class UsersApiImpl(
    private val domain: String,
    private val httpPipeline: HttpPipeline
) : UsersApi {

    private val gson = Gson()

    override fun register(registerRequest: RegisterRequest): CompletableFuture<BaseResponse> {
        val url = "$domain/api/users"
        val content = JsonContent(gson.toJson(registerRequest))
        val httpRequest = HttpRequest(HttpMethods.POST, url).applyJsonContent(content)
        val response = httpPipeline.handleRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), BaseResponse::class.java)
        }
    }

    override fun changePassword(changePasswordRequest: ChangePasswordRequest): CompletableFuture<BaseResponse> {
        val url = "$domain/api/users/password"
        val content = JsonContent(gson.toJson(changePasswordRequest))
        val httpRequest = HttpRequest(HttpMethods.PUT, url).applyJsonContent(content)
        val response = httpPipeline.handleRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), BaseResponse::class.java)
        }
    }

    override fun getUser(userId: UUID): CompletableFuture<GetUserResponse> {
        val url = "$domain/api/users/$userId"
        val httpRequest = HttpRequest(HttpMethods.GET, url)
        val response = httpPipeline.handleRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), GetUserResponse::class.java)
        }
    }

    override fun updateSocials(updateSocialsRequest: UpdateSocialsRequest): CompletableFuture<BaseResponse> {
        val url = "$domain/api/users/socials"
        val content = JsonContent(gson.toJson(updateSocialsRequest))
        val httpRequest = HttpRequest(HttpMethods.PUT, url).applyJsonContent(content)
        val response = httpPipeline.handleRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), BaseResponse::class.java)
        }
    }

    override fun updateAccount(updateAccountRequest: UpdateAccountRequest): CompletableFuture<BaseResponse> {
        val url = "$domain/api/users/account"
        val content = JsonContent(gson.toJson(updateAccountRequest))
        val httpRequest = HttpRequest(HttpMethods.PUT, url).applyJsonContent(content)
        val response = httpPipeline.handleRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), BaseResponse::class.java)
        }
    }

    override fun updateProfilePicture(picture: File): CompletableFuture<UpdateProfilePictureResponse> {
        val url = "$domain/api/users/picture"
        val multipartForm = MultipartFormContent().apply {
            formFields.add(MultipartFormFile(
                name = "newProfilePicture",
                fileName = picture.name,
                contentType = "image/${picture.extension}",
                picture
            ))
        }
        val httpRequest = HttpRequest(HttpMethods.POST, url).applyMultipartFormContent(multipartForm)
        val response = httpPipeline.handleRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), UpdateProfilePictureResponse::class.java)
        }
    }
}