package mangomessenger.http.pipelines

import mangomessenger.http.HttpRequest
import mangomessenger.http.HttpResponse
import java.util.concurrent.CompletableFuture

interface HttpInterceptor {
    fun intercept(httpRequest: HttpRequest) : CompletableFuture<HttpResponse>
}