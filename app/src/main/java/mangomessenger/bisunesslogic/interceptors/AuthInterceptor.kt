package mangomessenger.bisunesslogic.interceptors

import mangomessenger.bisunesslogic.data.TokenStorage
import mangomessenger.core.apis.SessionsApi
import mangomessenger.http.HttpRequest
import mangomessenger.http.HttpResponse
import mangomessenger.http.pipelines.HttpInterceptorMiddleware
import java.net.HttpURLConnection
import java.util.concurrent.CompletableFuture

class AuthInterceptor(
    private val tokenStorage: TokenStorage,
    private val sessionsApi: SessionsApi
    ) : HttpInterceptorMiddleware() {
    override fun intercept(httpRequest: HttpRequest): CompletableFuture<HttpResponse> {
        return interceptNext(httpRequest).thenCompose { httpResponse ->
            if (httpResponse.connection.responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                val tokens = tokenStorage.getTokens()
                val interceptAgain = sessionsApi
                    .refreshSessionAsync(tokens.refreshToken)
                    .thenCompose {
                        if (it.tokens != null) {
                            tokenStorage.updateTokens(it.tokens)
                        }

                        interceptNext(httpRequest)
                    }

                return@thenCompose interceptAgain
            }

            return@thenCompose CompletableFuture.completedFuture(httpResponse)
        }
    }
}