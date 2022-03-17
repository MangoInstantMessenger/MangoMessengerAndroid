package mangomessenger.core.jwt

import mangomessenger.http.HttpRequest
import mangomessenger.http.HttpResponse
import mangomessenger.http.pipelines.HttpInterceptorMiddleware
import java.util.concurrent.CompletableFuture

class JwtInterceptor(private val tokenProvider: () -> String) : HttpInterceptorMiddleware() {
    private val authorizationHeader = "Authorization"

    override fun intercept(httpRequest: HttpRequest): CompletableFuture<HttpResponse> {
        val modifiedRequest = httpRequest.copy().apply {
            headerFields[authorizationHeader] = "Bearer " + tokenProvider()
        }

        return interceptNext(modifiedRequest)
    }
}