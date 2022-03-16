package mangomessenger.tests.apis.communities

import mangomessenger.core.apis.CommunitiesApi
import mangomessenger.core.apis.CommunitiesApiImpl
import mangomessenger.core.apis.SessionsApi
import mangomessenger.core.apis.SessionsApiImpl
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.jwt.JwtInterceptor
import mangomessenger.http.HttpClient
import mangomessenger.http.HttpInterceptor
import mangomessenger.tests.apis.variables.Credentials
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetChatsTests {
    private var jwtToken: String = ""
    private lateinit var httpClient: HttpClient
    private lateinit var communitiesApi: CommunitiesApi
    private lateinit var sessionsApi: SessionsApi

    @Before
    fun before() {
        val jwtInterceptor = JwtInterceptor { jwtToken }
        val interceptors = ArrayList<HttpInterceptor>().apply {
            add(jwtInterceptor)
        }
        httpClient = HttpClient(interceptors)
        communitiesApi = CommunitiesApiImpl(httpClient)
        sessionsApi = SessionsApiImpl(httpClient)
    }

    @Test
    fun getChatsSuccess() {
        login()
        val getChatsResponse = communitiesApi.getChatsAsync().get()
        Assert.assertTrue(getChatsResponse.success)
    }

    @Test
    fun getChatsUnauthenticated() {
        val getChatsResponse = communitiesApi.getChatsAsync().get()
        Assert.assertFalse(getChatsResponse.success)
    }

    private fun login() {
        val loginRequest = LoginRequest(Credentials.Email, Credentials.Password)
        sessionsApi.loginAsync(loginRequest).thenAccept {
            jwtToken = it.tokens?.accessToken ?: ""
        }.get()
    }
}