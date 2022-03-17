package mangomessenger.core.jwt

import mangomessenger.core.apis.SessionsApiImpl
import mangomessenger.core.types.models.Tokens
import mangomessenger.http.HttpClient
import mangomessenger.http.HttpRequest
import mangomessenger.http.HttpResponse
import mangomessenger.http.pipelines.HttpInterceptorMiddleware
import mangomessenger.http.pipelines.HttpPipeline
import java.net.HttpURLConnection
import java.util.*
import java.util.concurrent.CompletableFuture

class AuthInterceptor(
    private val tokensGetter: () -> Tokens?,
    private val tokensSetter: (Tokens?) -> Unit)
    : HttpInterceptorMiddleware() {
    override fun intercept(httpRequest: HttpRequest): CompletableFuture<HttpResponse> {
        return interceptNext(httpRequest).thenCompose { httpResponse ->
            if (httpResponse.connection.responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                val token = tokensGetter()?.refreshToken?.toString().orEmpty()
                val interceptAgain = SessionsApiImpl(HttpPipeline())
                    .refreshSessionAsync(token)
                    .thenCompose {
                        tokensSetter(it.tokens)
                        interceptNext(httpRequest)
                    }

                return@thenCompose interceptAgain
            }

            return@thenCompose CompletableFuture.completedFuture(httpResponse)
        }
    }
}