package mangomessenger.http

import mangomessenger.http.declarations.responseStream
import java.net.HttpURLConnection
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
            val isNotGetMethod = httpRequest.method.compareTo(HttpMethods.GET, true) != 0
            if (isNotGetMethod && httpRequest.body != null) {
                connection.outputStream.run {
                    httpRequest.body!!.writeToStream(this)
                    close()
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

