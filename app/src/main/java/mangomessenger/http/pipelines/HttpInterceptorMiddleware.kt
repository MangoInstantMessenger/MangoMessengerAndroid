package mangomessenger.http.pipelines

import mangomessenger.http.HttpRequest
import mangomessenger.http.HttpResponse
import java.util.concurrent.CompletableFuture

abstract class HttpInterceptorMiddleware : HttpInterceptor {
    private lateinit var next: HttpInterceptor

    fun linkWith(middleware: HttpInterceptor) {
        next = middleware
    }

    protected fun interceptNext(httpRequest: HttpRequest) : CompletableFuture<HttpResponse> {
        return next.intercept(httpRequest)
    }
}