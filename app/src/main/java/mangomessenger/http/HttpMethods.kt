package mangomessenger.http

sealed class HttpMethods private constructor() {
    companion object {
        const val GET = "GET"
        const val POST = "POST"
        const val PUT = "PUT"
        const val DELETE = "DELETE"
    }
}