package tests.apis.sessions

import mangomessenger.core.apis.SessionsApi
import mangomessenger.core.apis.SessionsApiImpl
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.jwt.JwtInterceptor
import mangomessenger.http.HttpClient
import mangomessenger.http.HttpInterceptor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tests.apis.variables.Credentials

class DeleteAllSessionsTests {
    private var jwtToken: String = ""
    private lateinit var httpClient: HttpClient
    private lateinit var sessionsApi: SessionsApi

    @Before
    fun before() {
        val jwtInterceptor = JwtInterceptor { jwtToken }
        val interceptors = ArrayList<HttpInterceptor>().apply { add(jwtInterceptor) }
        httpClient = HttpClient(interceptors)
        sessionsApi = SessionsApiImpl(httpClient)
    }

    @Test
    fun deleteSessionsSuccess() {
        val loginRequest = LoginRequest(Credentials.Email, Credentials.Password)
        sessionsApi.loginAsync(loginRequest).thenAccept {
             jwtToken = it.tokens?.accessToken.orEmpty()
        }.get()

        val response = sessionsApi.deleteAllSessionsAsync().get()
        Assert.assertTrue(response.success)
    }

    @Test
    fun deleteSessionsUnauthenticated() {
        val response = sessionsApi.deleteAllSessionsAsync().get()
        Assert.assertFalse(response.success)
        Assert.assertEquals(response.statusCode, 401)
    }
}