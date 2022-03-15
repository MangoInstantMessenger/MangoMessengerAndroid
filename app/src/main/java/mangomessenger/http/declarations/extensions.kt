package mangomessenger.http.declarations

import mangomessenger.http.HttpRequest
import mangomessenger.http.JsonContent
import mangomessenger.http.MultipartFormContent
import java.io.InputStream
import java.net.HttpURLConnection

private const val contentType = "Content-Type"

fun HttpURLConnection.responseStream(): InputStream? {
    if (this.responseCode in 200..299) {
        return this.inputStream
    }
    return this.errorStream
}

fun HttpRequest.applyMultipartFormContent(multipartFormContent: MultipartFormContent) : HttpRequest {
    return this.apply {
        body = multipartFormContent
        headerFields[contentType] = "multipart/form-data; boundary=${multipartFormContent.boundary}"
    }
}

fun HttpRequest.applyJsonContent(jsonContent: JsonContent) : HttpRequest {
    return this.apply {
        body = jsonContent
        headerFields[contentType] = "application/json"
    }
}