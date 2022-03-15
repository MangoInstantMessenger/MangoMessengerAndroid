package tests.apis.sessions

import mangomessenger.core.apis.SessionsApi
import mangomessenger.core.apis.SessionsApiImpl
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.apis.responses.LoginResponse
import mangomessenger.core.jwt.JwtInterceptor
import mangomessenger.http.HttpClient
import mangomessenger.http.HttpInterceptor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tests.apis.variables.Credentials
import java.util.*
import kotlin.collections.ArrayList

class RefreshSessionTests {
    private var loginResponse: LoginResponse = LoginResponse(null)
    private lateinit var httpClient: HttpClient
    private lateinit var sessionsApi: SessionsApi

    @Before
    fun before() {
        val jwtInterceptor = JwtInterceptor { loginResponse.tokens?.accessToken ?: "" }
        val interceptors = ArrayList<HttpInterceptor>().apply { add(jwtInterceptor) }
        httpClient = HttpClient(interceptors)
        sessionsApi = SessionsApiImpl(httpClient)
    }

    @Test
    fun refreshSessionSuccess() {
        val loginRequest = LoginRequest(Credentials.Email, Credentials.Password)
        loginResponse = sessionsApi.loginAsync(loginRequest).get()
        val refreshToken = loginResponse.tokens?.refreshToken.toString()
        val response = sessionsApi.refreshSessionAsync(refreshToken).get()
        Assert.assertTrue(response.success)
    }

    @Test
    fun refreshSessionUnauthenticated() {
        val refreshToken = UUID.randomUUID().toString()
        val response = sessionsApi.refreshSessionAsync(refreshToken).get()
        Assert.assertFalse(response.success)
    }
}