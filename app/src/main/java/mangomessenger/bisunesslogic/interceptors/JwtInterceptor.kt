package mangomessenger.bisunesslogic.interceptors

import mangomessenger.bisunesslogic.data.TokensRepository
import mangomessenger.http.HttpRequest
import mangomessenger.http.HttpResponse
import mangomessenger.http.pipelines.HttpInterceptorMiddleware
import java.util.concurrent.CompletableFuture

class JwtInterceptor(private val tokensRepository: TokensRepository) : HttpInterceptorMiddleware() {
    private val authorizationHeader = "Authorization"

    override fun intercept(httpRequest: HttpRequest): CompletableFuture<HttpResponse> {
        val modifiedRequest = httpRequest.copy().apply {
            val tokens = tokensRepository.getTokens()
            headerFields[authorizationHeader] = "Bearer " + tokens.accessToken
        }

        return interceptNext(modifiedRequest)
    }
}