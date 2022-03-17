package mangomessenger.http

data class HttpRequest(
    var method: String,
    var url: String,
    var body: HttpContent = EmptyContent(),
    var queryParameters: MutableMap<String, String> = HashMap(),
    var headerFields: MutableMap<String, String> = HashMap())
