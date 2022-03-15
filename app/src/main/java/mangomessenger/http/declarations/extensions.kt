package mangomessenger.http.declarations

import mangomessenger.http.HttpMethods
import mangomessenger.http.HttpRequest
import java.io.InputStream
import java.net.HttpURLConnection

fun HttpURLConnection.responseStream(): InputStream? {
    if (this.responseCode in 200..299) {
        return this.inputStream
    }
    return this.errorStream
}

val HttpRequest.shouldWriteBody: Boolean
    get() {
        val isNotGetRequest = this.method.compareTo(HttpMethods.GET, true) != 0
        return isNotGetRequest.and(this.body != null)
    }