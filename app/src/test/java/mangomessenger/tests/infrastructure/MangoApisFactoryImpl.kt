package mangomessenger.tests.infrastructure

import mangomessenger.core.apis.CommunitiesApi
import mangomessenger.core.apis.CommunitiesApiImpl
import mangomessenger.core.apis.SessionsApi
import mangomessenger.core.apis.SessionsApiImpl
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

    override fun createMessagesApi(): MessagesApi {
        val httpPipeline = httpPipelineFactory.createHttpPipeline()
        return MessagesApiImpl("https://back.mangomessenger.company", httpPipeline)
    }
}