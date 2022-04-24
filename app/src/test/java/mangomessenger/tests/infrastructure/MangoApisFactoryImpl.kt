package mangomessenger.tests.infrastructure

import mangomessenger.core.apis.*
import mangomessenger.core.apis.factories.MangoApisFactory
import mangomessenger.http.pipelines.HttpPipelineFactory

class MangoApisFactoryImpl(private val httpPipelineFactory: HttpPipelineFactory) : MangoApisFactory {
    override fun createCommunityApi(): CommunitiesApi {
        val httpPipeline = httpPipelineFactory.createHttpPipeline()
        return CommunitiesApiImpl("https://back.mangomessenger.company", httpPipeline)
    }

    override fun createSessionsApi(): SessionsApi {
        val httpPipeline = httpPipelineFactory.createHttpPipeline()
        return SessionsApiImpl("https://back.mangomessenger.company", httpPipeline)
    }

    override fun createUsersApi(): UsersApi {
        val httpPipeline = httpPipelineFactory.createHttpPipeline()
        return UsersApiImpl("https://back.mangomessenger.company", httpPipeline)
    }
}