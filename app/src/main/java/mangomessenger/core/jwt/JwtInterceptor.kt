package mangomessenger.core.jwt

import mangomessenger.http.HttpInterceptor
import mangomessenger.http.HttpRequest

class JwtInterceptor(private val tokenProvider: () -> String) : HttpInterceptor {
    private val authorizationHeader = "Authorization"

    override fun intercept(request: HttpRequest): HttpRequest {
        return request.apply {
            headerFields[authorizationHeader] = "Bearer " + tokenProvider()
        }
    }
}