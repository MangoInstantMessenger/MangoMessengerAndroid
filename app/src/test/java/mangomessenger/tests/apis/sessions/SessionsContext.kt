package mangomessenger.tests.apis.sessions

import mangomessenger.core.apis.SessionsApi
import mangomessenger.core.apis.SessionsApiImpl
import mangomessenger.core.apis.utils.SafetyUnauthenticatedInterceptor
import mangomessenger.core.jwt.JwtInterceptor
import mangomessenger.http.pipelines.HttpPipeline

class SessionsContext {
    var jwtToken: String = ""
    var sessionsApi: SessionsApi

    init {
        val jwtInterceptor = JwtInterceptor { jwtToken }
        val httpPipeline = HttpPipeline()
        httpPipeline.addInterceptor(SafetyUnauthenticatedInterceptor())
        httpPipeline.addInterceptor(jwtInterceptor)
        sessionsApi = SessionsApiImpl(httpPipeline)
    }
}