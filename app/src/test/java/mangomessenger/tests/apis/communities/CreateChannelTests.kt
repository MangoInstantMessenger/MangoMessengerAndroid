package mangomessenger.tests.apis.communities

import mangomessenger.bisunesslogic.data.TokenStorage
import mangomessenger.bisunesslogic.data.TokenStorageImpl
import mangomessenger.bisunesslogic.services.SignInService
import mangomessenger.bisunesslogic.services.SignInServiceImpl
import mangomessenger.core.apis.CommunitiesApi
import mangomessenger.core.apis.requests.CreateCommunityRequest
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.tests.apis.asserts.MangoAsserts
import mangomessenger.tests.infrastructure.HttpPipelineFactoryImpl
import mangomessenger.tests.infrastructure.MangoApisFactoryImpl
import mangomessenger.tests.infrastructure.constants.EnvironmentVariables
import org.junit.Before
import org.junit.Test
import java.util.*

class CreateChannelTests {
    private lateinit var tokenStorage: TokenStorage
    private lateinit var communitiesApi: CommunitiesApi
    private lateinit var signInService: SignInService

    @Before
    fun before() {
        tokenStorage = TokenStorageImpl()
        val pipelineFactory = HttpPipelineFactoryImpl(tokenStorage)
        val mangoApisFactory = MangoApisFactoryImpl(pipelineFactory)
        val sessionsApi = mangoApisFactory.createSessionsApi()
        communitiesApi = mangoApisFactory.createCommunityApi()
        signInService = SignInServiceImpl(sessionsApi, tokenStorage)
    }

    @Test
    fun createChannelSuccess() {
        val title = UUID.randomUUID().toString()
        val description = UUID.randomUUID().toString()
        val createCommunityRequest = CreateCommunityRequest(title, description)
        val loginRequest = LoginRequest(EnvironmentVariables.testEmail(), EnvironmentVariables.testPassword())
        val responseTask = signInService
            .signIn(loginRequest)
            .thenCompose { communitiesApi.createChannelAsync(createCommunityRequest) }
            .thenApply { createCommunityResponse ->
                signInService.signOut()
                return@thenApply createCommunityResponse
            }
        val response = responseTask.get()
        MangoAsserts.assertSuccessResponse(response)
    }

    @Test
    fun createChannelUnauthorized() {
        val title = UUID.randomUUID().toString()
        val description = UUID.randomUUID().toString()
        val createCommunityRequest = CreateCommunityRequest(title, description)
        val responseTask = communitiesApi.createChannelAsync(createCommunityRequest)
        val response = responseTask.get()
        MangoAsserts.assertUnauthorized(response)
    }
}