package mangomessenger.bisunesslogic.interceptors

import mangomessenger.bisunesslogic.data.TokenStorage
import mangomessenger.http.HttpRequest
import mangomessenger.http.HttpResponse
import mangomessenger.http.pipelines.HttpInterceptorMiddleware
import java.util.concurrent.CompletableFuture

class JwtInterceptor(private val tokenStorage: TokenStorage) : HttpInterceptorMiddleware() {
    private val authorizationHeader = "Authorization"

    override fun intercept(httpRequest: HttpRequest): CompletableFuture<HttpResponse> {
        val modifiedRequest = httpRequest.copy().apply {
            val tokens = tokenStorage.getTokens()
            headerFields[authorizationHeader] = "Bearer " + tokens.accessToken
        }

        return interceptNext(modifiedRequest)
    }
}