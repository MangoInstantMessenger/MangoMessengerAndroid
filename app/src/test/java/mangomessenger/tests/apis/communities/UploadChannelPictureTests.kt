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
import java.io.File
import java.util.*

class UploadChannelPictureTests {
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
    fun uploadChannelPictureSuccess() {
        val chatId = UUID.fromString("c99cc40d-dd1c-42fd-9da7-1157184f9781")
        val picture = File(EnvironmentVariables.testImageFilePath())
        val loginRequest = LoginRequest(EnvironmentVariables.testEmail(), EnvironmentVariables.testPassword())
        val responseTask = signInService
            .signIn(loginRequest)
            .thenCompose { communitiesApi.uploadChannelPictureAsync(chatId, picture) }
        val response = responseTask.get()
        MangoAsserts.assertSuccessResponse(response)
    }
}