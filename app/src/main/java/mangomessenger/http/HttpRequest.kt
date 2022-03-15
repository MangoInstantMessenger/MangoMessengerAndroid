package mangomessenger.http

data class HttpRequest(var method: String, var url: String, var body: HttpContent? = null) {
    val queryParameters: MutableMap<String, String> = HashMap()
    val headerFields: MutableMap<String, String> = HashMap()
}
