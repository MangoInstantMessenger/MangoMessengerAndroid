package mangomessenger.core.apis.utils

import mangomessenger.http.HttpRequest
import mangomessenger.http.HttpResponse
import mangomessenger.http.pipelines.HttpInterceptorMiddleware
import java.util.concurrent.CompletableFuture

class SafetyUnauthenticatedInterceptor : HttpInterceptorMiddleware() {
    override fun intercept(httpRequest: HttpRequest): CompletableFuture<HttpResponse> {
        return interceptNext(httpRequest).orUnauthenticated()
    }
}