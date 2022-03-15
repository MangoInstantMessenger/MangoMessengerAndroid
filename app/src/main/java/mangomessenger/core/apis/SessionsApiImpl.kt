package mangomessenger.core.apis

import com.google.gson.Gson
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.apis.responses.BaseResponse
import mangomessenger.core.apis.responses.LoginResponse
import mangomessenger.core.apis.utils.safetyHttpRequest
import mangomessenger.http.HttpClient
import mangomessenger.http.HttpMethods
import mangomessenger.http.HttpRequest
import java.util.concurrent.CompletableFuture

class SessionsApiImpl(private val httpClient: HttpClient) : SessionsApi {
    private val gson = Gson()
    private val domain = "https://back.mangomessenger.company"

    override fun loginAsync(request: LoginRequest): CompletableFuture<LoginResponse> {
        val url = "$domain/api/sessions"
        val httpRequest = HttpRequest(HttpMethods.POST, url, gson.toJson(request))
        val response = httpClient.safetyHttpRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), LoginResponse::class.java)
        }
    }

    override fun refreshSessionAsync(refreshToken: String): CompletableFuture<LoginResponse> {
        val url = "$domain/api/sessions/$refreshToken"
        val httpRequest = HttpRequest(HttpMethods.POST, url, String())
        val response = httpClient.safetyHttpRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), LoginResponse::class.java)
        }
    }

    override fun deleteCurrentSessionAsync(refreshToken: String): CompletableFuture<BaseResponse> {
        val url = "$domain/api/sessions/$refreshToken"
        val httpRequest = HttpRequest(HttpMethods.DELETE, url)
        val response = httpClient.safetyHttpRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), BaseResponse::class.java)
        }
    }

    override fun deleteAllSessionsAsync(): CompletableFuture<BaseResponse> {
        val url = "$domain/api/sessions"
        val httpRequest = HttpRequest(HttpMethods.DELETE, url)
        val response = httpClient.safetyHttpRequest(httpRequest)
        return response.thenApply {
            return@thenApply gson.fromJson(String(it.data), BaseResponse::class.java)
        }
    }
}



