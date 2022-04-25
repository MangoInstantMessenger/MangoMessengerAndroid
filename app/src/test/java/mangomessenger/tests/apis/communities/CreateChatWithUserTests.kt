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
import mangomessenger.utils.UuidUtils
import org.junit.Before
import org.junit.Test
import java.util.*

class CreateChatWithUserTests {
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
    fun createChatWithUserSuccess() {
        val userId = UUID.fromString("e77cf2cb-3f3a-4f0b-ac5a-90a3263d075a")
        val loginRequest = LoginRequest(EnvironmentVariables.testEmail(), EnvironmentVariables.testPassword())
        val responseTask = signInService
            .signIn(loginRequest)
            .thenCompose { communitiesApi.createChatWithUserAsync(userId) }
        val response = responseTask.get()
        MangoAsserts.assertSuccessResponse(response)
    }

    @Test
    fun createChatWithUserUnauthorized() {
        val userId = UUID.fromString("30082298-e6e6-4aca-a8b9-89ba06d12e4f")
        val responseTask = communitiesApi.createChatWithUserAsync(userId)
        val response = responseTask.get()
        MangoAsserts.assertUnauthorized(response)
    }

    @Test
    fun createChatWithNonExistingUser() {
        val userId = UuidUtils.emptyUUID()
        val loginRequest = LoginRequest(EnvironmentVariables.testEmail(), EnvironmentVariables.testPassword())
        val responseTask = signInService
            .signIn(loginRequest)
            .thenCompose { communitiesApi.createChatWithUserAsync(userId) }
        val response = responseTask.get()
        MangoAsserts.assertFailedResponse(response)
    }
}