package mangomessenger.core.apis

import com.google.gson.Gson
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.apis.responses.BaseResponse
import mangomessenger.core.apis.responses.LoginResponse
import mangomessenger.http.HttpMethods
import mangomessenger.http.HttpRequest
import mangomessenger.http.JsonContent
import mangomessenger.http.declarations.applyJsonContent
import mangomessenger.http.pipelines.HttpPipeline
import java.util.*
import java.util.concurrent.CompletableFuture

class SessionsApiImpl(
    private val domain: String,
    private val httpPipeline: HttpPipeline
    ) : SessionsApi {

    private val gson = Gson()

    override fun loginAsync(request: LoginRequest): CompletableFuture<LoginResponse> {
        val url = "$domain/api/sessions"
        val content = JsonContent(gson.toJson(request))
        val httpRequest = HttpRequest(HttpMethods.POST, url).applyJsonContent(content)
        val response = httpPipeline.handleRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), LoginResponse::class.java)
        }
    }

    override fun refreshSessionAsync(refreshToken: UUID): CompletableFuture<LoginResponse> {
        val url = "$domain/api/sessions/$refreshToken"
        val httpRequest = HttpRequest(HttpMethods.POST, url)
        val response = httpPipeline.handleRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), LoginResponse::class.java)
        }
    }
    override fun deleteCurrentSessionAsync(refreshToken: UUID): CompletableFuture<BaseResponse> {
        val url = "$domain/api/sessions/$refreshToken"
        val httpRequest = HttpRequest(HttpMethods.DELETE, url)
        val response = httpPipeline.handleRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), BaseResponse::class.java)
        }
    }

    override fun deleteAllSessionsAsync(): CompletableFuture<BaseResponse> {
        val url = "$domain/api/sessions"
        val httpRequest = HttpRequest(HttpMethods.DELETE, url)
        val response = httpPipeline.handleRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), BaseResponse::class.java)
        }
    }
}



