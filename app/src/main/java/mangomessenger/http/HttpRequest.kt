package mangomessenger.http

data class HttpRequest(var method: String, var url: String, var body: Any? = null) {
    val queryParameters: MutableMap<String, String> = HashMap()
    val headerFields: MutableMap<String, String> = HashMap<String, String>().apply {
        put("Content-Type", "application/json")
    }
}
