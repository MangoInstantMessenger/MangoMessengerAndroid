package mangomessenger.tests.http

import mangomessenger.core.apis.CommunitiesApi
import mangomessenger.core.apis.CommunitiesApiImpl
import mangomessenger.core.apis.SessionsApi
import mangomessenger.core.apis.SessionsApiImpl
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.jwt.AuthInterceptor
import mangomessenger.core.jwt.JwtInterceptor
import mangomessenger.core.types.models.Tokens
import mangomessenger.http.pipelines.HttpPipeline
import mangomessenger.tests.apis.variables.Credentials
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HttpPipelineTests {
    private var httpPipeline = HttpPipeline()
    private var tokens: Tokens? = null
    private lateinit var sessionsApi: SessionsApi
    private lateinit var communitiesApi: CommunitiesApi

    @Before
    fun before() {
        httpPipeline = HttpPipeline()
        httpPipeline.addInterceptor(JwtInterceptor { tokens?.accessToken.orEmpty() })
        httpPipeline.addInterceptor(AuthInterceptor({ tokens }, { tokens = it }))
        sessionsApi = SessionsApiImpl(httpPipeline)
        communitiesApi = CommunitiesApiImpl(httpPipeline)
    }

    @Test
    fun pipelineTest() {
        val loginRequest = LoginRequest(Credentials.Email, Credentials.Password)
        tokens = sessionsApi.loginAsync(loginRequest).get().tokens
        Assert.assertTrue(communitiesApi.getChatsAsync().get().success)
        tokens = tokens?.copy(accessToken = "")
        Assert.assertTrue(communitiesApi.getChatsAsync().get().success)
    }
}