package mangomessenger.core.apis.utils

import com.google.gson.Gson
import mangomessenger.http.HttpClient
import mangomessenger.http.HttpRequest
import mangomessenger.http.HttpResponse
import java.net.HttpURLConnection
import java.util.concurrent.CompletableFuture

fun CompletableFuture<HttpResponse>.orUnauthenticated(): CompletableFuture<HttpResponse> {
    return this.thenApply {
        if (it.connection.responseCode == HttpURLConnection.HTTP_UNAUTHORIZED && it.data.isEmpty()) {
            val data = Gson().toJson(HttpResponseUtils.unauthenticatedResponse()).encodeToByteArray()
            return@thenApply it.copy(connection = it.connection, data = data)
        }

        return@thenApply it
    }
}

// TODO("Remove it when WebAPI unauthenticated response fixed.")
fun HttpClient.safetyHttpRequest(httpRequest: HttpRequest): CompletableFuture<HttpResponse> {
    return this.requestAsync(httpRequest).orUnauthenticated()
}