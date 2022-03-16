package mangomessenger.tests.apis.communities

import mangomessenger.core.apis.CommunitiesApi
import mangomessenger.core.apis.CommunitiesApiImpl
import mangomessenger.core.apis.SessionsApi
import mangomessenger.core.apis.SessionsApiImpl
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.jwt.JwtInterceptor
import mangomessenger.http.HttpClient
import mangomessenger.http.HttpInterceptor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import mangomessenger.tests.apis.variables.Credentials
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class UploadChannelPictureTests {
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
    fun uploadChannelPictureSuccess() {
        // TODO: Rewrite this test to use mocked UUID and File.
        // TODO: Probably use relative filePath.
        login()
        val chatId = UUID.fromString("fe3a4980-f508-4604-8788-c64e74702b23")
        val file = File("C:\\Users\\#hash#\\Pictures\\mangoicon.png")
        val response = communitiesApi.uploadChannelPictureAsync(chatId, file).get()
        Assert.assertTrue(response.success)
    }

    private fun login() {
        val loginRequest = LoginRequest(Credentials.Email, Credentials.Password)
        sessionsApi.loginAsync(loginRequest).thenAccept {
            jwtToken = it.tokens?.accessToken ?: ""
        }.get()
    }
}