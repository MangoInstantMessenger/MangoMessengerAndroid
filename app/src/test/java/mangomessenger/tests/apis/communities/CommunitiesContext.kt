package mangomessenger.tests.apis.communities

import mangomessenger.core.apis.CommunitiesApi
import mangomessenger.core.apis.CommunitiesApiImpl
import mangomessenger.core.apis.SessionsApi
import mangomessenger.core.apis.SessionsApiImpl
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.apis.utils.SafetyUnauthenticatedInterceptor
import mangomessenger.core.jwt.JwtInterceptor
import mangomessenger.http.HttpClient
import mangomessenger.http.pipelines.HttpInterceptor
import mangomessenger.http.pipelines.HttpPipeline
import mangomessenger.tests.apis.variables.Credentials

class CommunitiesContext {
    var jwtToken: String = ""
    var communitiesApi: CommunitiesApi
    var sessionsApi: SessionsApi

    init {
        val jwtInterceptor = JwtInterceptor { jwtToken }
        val httpPipeline = HttpPipeline()
        httpPipeline.addInterceptor(SafetyUnauthenticatedInterceptor())
        httpPipeline.addInterceptor(jwtInterceptor)
        communitiesApi = CommunitiesApiImpl(httpPipeline)
        sessionsApi = SessionsApiImpl(httpPipeline)
    }

    fun login() {
        val loginRequest = LoginRequest(Credentials.Email, Credentials.Password)
        sessionsApi.loginAsync(loginRequest).thenAccept {
            jwtToken = it.tokens?.accessToken ?: ""
        }.get()
    }
}