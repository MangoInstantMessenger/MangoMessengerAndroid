package mangomessenger.http.pipelines

import mangomessenger.http.HttpClient
import mangomessenger.http.HttpRequest
import mangomessenger.http.HttpResponse
import java.util.concurrent.CompletableFuture

class HttpPipeline {
    private var interceptorChain: HttpInterceptor = HttpClient()

    fun addInterceptor(httpInterceptorMiddleware: HttpInterceptorMiddleware) {
        httpInterceptorMiddleware.linkWith(interceptorChain)
        interceptorChain = httpInterceptorMiddleware
    }

    fun handleRequest(httpRequest: HttpRequest) : CompletableFuture<HttpResponse> {
        return interceptorChain.intercept(httpRequest)
    }
}