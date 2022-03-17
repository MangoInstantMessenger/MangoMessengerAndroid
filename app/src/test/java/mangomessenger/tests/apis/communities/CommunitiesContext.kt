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

class CommunitiesContext {
    var jwtToken: String = ""
    var httpClient: HttpClient
    var communitiesApi: CommunitiesApi
    var sessionsApi: SessionsApi

    init {
        val jwtInterceptor = JwtInterceptor { jwtToken }
        val interceptors = ArrayList<HttpInterceptor>().apply {
            add(jwtInterceptor)
        }
        httpClient = HttpClient(interceptors)
        communitiesApi = CommunitiesApiImpl(httpClient)
        sessionsApi = SessionsApiImpl(httpClient)
    }

    fun login() {
        val loginRequest = LoginRequest(Credentials.Email, Credentials.Password)
        sessionsApi.loginAsync(loginRequest).thenAccept {
            jwtToken = it.tokens?.accessToken ?: ""
        }.get()
    }
}