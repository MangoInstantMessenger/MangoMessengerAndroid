package mangomessenger.http

import mangomessenger.http.declarations.responseStream
import mangomessenger.http.declarations.shouldWriteBody
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.util.concurrent.CompletableFuture

class HttpClient(private val interceptors: List<HttpInterceptor>) {

    fun requestAsync(httpRequest: HttpRequest): CompletableFuture<HttpResponse> {
        return CompletableFuture.supplyAsync {
            return@supplyAsync request(httpRequest)
        }
    }

    private fun request(httpRequest: HttpRequest): HttpResponse {
        val handledHttpRequest = interceptors.fold(httpRequest) {
                modifiedHttpRequest: HttpRequest,
                interceptor: HttpInterceptor -> interceptor.intercept(modifiedHttpRequest)
        }
        val connection: HttpURLConnection = createConnection(handledHttpRequest)

        try {
            if (httpRequest.shouldWriteBody) {
                connection.outputStream.writer().let {
                    it.write(httpRequest.body.toString())
                    it.flush()
                    it.close()
                }
            }

            connection.connect()
            val data = connection.responseStream()?.readBytes() ?: ByteArray(0)
            return HttpResponse(connection, data)
        }
        finally {
            connection.disconnect()
        }
    }

    private fun createConnection(httpRequest: HttpRequest): HttpURLConnection {
        val url = httpRequest.url + httpRequest.queryParameters.let { queryParams ->
            if (queryParams.isEmpty()) {
                return@let String()
            }

            return@let queryParams.map { "${it.key}=${it.value}" }.joinToString("&")
        }
        val connection = URL(url).openConnection() as HttpURLConnection
        return connection.apply {
            requestMethod = httpRequest.method
            doInput = true
            doOutput = true
            httpRequest.headerFields.forEach { setRequestProperty(it.key, it.value) }
        }
    }
}

