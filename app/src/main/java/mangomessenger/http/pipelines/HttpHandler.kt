package mangomessenger.http.pipelines

import mangomessenger.http.HttpClient
import mangomessenger.http.HttpRequest
import mangomessenger.http.HttpResponse
import java.util.concurrent.CompletableFuture

class HttpHandler(private val httpClient: HttpClient) : HttpInterceptor {
    override fun intercept(httpRequest: HttpRequest): CompletableFuture<HttpResponse> {
        return httpClient.requestAsync(httpRequest)
    }
}