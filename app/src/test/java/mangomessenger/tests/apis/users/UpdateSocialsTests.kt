package mangomessenger.tests.apis.users

import mangomessenger.bisunesslogic.data.TokenStorage
import mangomessenger.bisunesslogic.data.TokenStorageImpl
import mangomessenger.bisunesslogic.services.SignInService
import mangomessenger.bisunesslogic.services.SignInServiceImpl
import mangomessenger.core.apis.UsersApi
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.apis.requests.UpdateSocialsRequest
import mangomessenger.tests.apis.asserts.MangoAsserts
import mangomessenger.tests.infrastructure.HttpPipelineFactoryImpl
import mangomessenger.tests.infrastructure.MangoApisFactoryImpl
import mangomessenger.tests.infrastructure.constants.EnvironmentVariables
import org.junit.Before
import org.junit.Test

class UpdateSocialsTests {
    private lateinit var tokenStorage: TokenStorage
    private lateinit var usersApi: UsersApi
    private lateinit var signInService: SignInService

    @Before
    fun before() {
        tokenStorage = TokenStorageImpl()
        val pipelineFactory = HttpPipelineFactoryImpl(tokenStorage)
        val mangoApisFactory = MangoApisFactoryImpl(pipelineFactory)
        usersApi = mangoApisFactory.createUsersApi()
        signInService = SignInServiceImpl(mangoApisFactory.createSessionsApi(), tokenStorage)
    }

    @Test
    fun updateSocialsSuccess() {
        val updateSocialsRequest = UpdateSocialsRequest(
            facebook = "TestFacebook",
            twitter = "TestTwitter",
            linkedIn = "TestLinkedIn",
            instagram = "TestInstagram"
        )
        val loginRequest = LoginRequest(
            EnvironmentVariables.testEmail(),
            EnvironmentVariables.testPassword())
        val responseTask = signInService
            .signIn(loginRequest)
            .thenCompose {
                usersApi.updateSocials(updateSocialsRequest)
            }
            .thenCompose { updateSocialsResponse ->
                signInService.signOut().thenApply { updateSocialsResponse }
            }
        val response = responseTask.get()
        MangoAsserts.assertSuccessResponse(response)
    }
}