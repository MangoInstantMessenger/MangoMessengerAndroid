package mangomessenger.tests.apis.communities

import mangomessenger.bisunesslogic.data.TokenStorage
import mangomessenger.bisunesslogic.data.TokenStorageImpl
import mangomessenger.bisunesslogic.services.SignInService
import mangomessenger.bisunesslogic.services.SignInServiceImpl
import mangomessenger.core.apis.CommunitiesApi
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.tests.apis.asserts.MangoAsserts
import mangomessenger.tests.infrastructure.HttpPipelineFactoryImpl
import mangomessenger.tests.infrastructure.MangoApisFactoryImpl
import mangomessenger.tests.infrastructure.constants.EnvironmentVariables
import org.junit.Before
import org.junit.Test

class GetChatsTests {
    private lateinit var tokenStorage: TokenStorage
    private lateinit var communitiesApi: CommunitiesApi
    private lateinit var signInService: SignInService

    @Before
    fun before() {
        tokenStorage = TokenStorageImpl()
        val pipelineFactory = HttpPipelineFactoryImpl(tokenStorage)
        val mangoApisFactory = MangoApisFactoryImpl(pipelineFactory)
        signInService = SignInServiceImpl(mangoApisFactory.createSessionsApi(), tokenStorage)
        communitiesApi = mangoApisFactory.createCommunityApi()
    }

    @Test
    fun getChatsSuccess() {
        val loginRequest = LoginRequest(EnvironmentVariables.testEmail(), EnvironmentVariables.testPassword())
        val getChatsTask = signInService
            .signIn(loginRequest)
            .thenCompose { communitiesApi.getChatsAsync() }
        val response = getChatsTask.get()
        MangoAsserts.assertSuccessResponse(response)
    }

    @Test
    fun getChatsFailed() {
        val getChatsTask = communitiesApi.getChatsAsync()
        val response = getChatsTask.get()
        MangoAsserts.assertFailedResponse(response)
    }
}