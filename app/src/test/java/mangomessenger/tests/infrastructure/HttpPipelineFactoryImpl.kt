package mangomessenger.tests.infrastructure

import mangomessenger.bisunesslogic.data.TokenStorage
import mangomessenger.bisunesslogic.interceptors.AuthInterceptor
import mangomessenger.bisunesslogic.interceptors.JwtInterceptor
import mangomessenger.core.apis.SessionsApiImpl
import mangomessenger.core.apis.utils.SafetyUnauthenticatedInterceptor
import mangomessenger.http.HttpClient
import mangomessenger.http.pipelines.HttpHandler
import mangomessenger.http.pipelines.HttpPipeline
import mangomessenger.http.pipelines.HttpPipelineFactory
import mangomessenger.http.pipelines.HttpPipelineFactoryDefault
import mangomessenger.tests.infrastructure.constants.EnvironmentVariables

class HttpPipelineFactoryImpl(private val tokenStorage: TokenStorage) : HttpPipelineFactory {
    override fun createHttpPipeline(): HttpPipeline {
        val httpClient = HttpClient()
        val httpHandler = HttpHandler(httpClient)
        val httpPipeline = HttpPipeline(httpHandler)
        val defaultPipeline = HttpPipelineFactoryDefault().createHttpPipeline()
        val sessionsApi = SessionsApiImpl("https://back.mangomessenger.company", defaultPipeline)
        httpPipeline.addInterceptor(AuthInterceptor(tokenStorage, sessionsApi))
        httpPipeline.addInterceptor(JwtInterceptor(tokenStorage))
        httpPipeline.addInterceptor(SafetyUnauthenticatedInterceptor())
        return httpPipeline
    }
}