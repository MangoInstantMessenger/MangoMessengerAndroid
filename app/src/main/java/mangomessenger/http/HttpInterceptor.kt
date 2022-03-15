package mangomessenger.http

interface HttpInterceptor {
    fun intercept(request: HttpRequest): HttpRequest
}