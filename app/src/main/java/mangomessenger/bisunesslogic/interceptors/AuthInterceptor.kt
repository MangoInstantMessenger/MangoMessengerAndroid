package mangomessenger.bisunesslogic.interceptors

import mangomessenger.bisunesslogic.data.TokensRepository
import mangomessenger.core.apis.SessionsApiImpl
import mangomessenger.http.HttpRequest
import mangomessenger.http.HttpResponse
import mangomessenger.http.pipelines.HttpInterceptorMiddleware
import mangomessenger.http.pipelines.HttpPipelineFactoryDefault
import java.net.HttpURLConnection
import java.util.concurrent.CompletableFuture

class AuthInterceptor(private val tokensRepository: TokensRepository) : HttpInterceptorMiddleware() {
    override fun intercept(httpRequest: HttpRequest): CompletableFuture<HttpResponse> {
        return interceptNext(httpRequest).thenCompose { httpResponse ->
            if (httpResponse.connection.responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                val tokens = tokensRepository.getTokens()
                val httpPipelineFactory = HttpPipelineFactoryDefault()
                val interceptAgain = SessionsApiImpl(httpPipelineFactory.createHttpPipeline())
                    .refreshSessionAsync(tokens.refreshToken)
                    .thenCompose {
                        if (it.tokens != null) {
                            tokensRepository.updateTokens(it.tokens)
                        }

                        interceptNext(httpRequest)
                    }

                return@thenCompose interceptAgain
            }

            return@thenCompose CompletableFuture.completedFuture(httpResponse)
        }
    }
}