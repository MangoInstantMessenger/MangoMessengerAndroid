package mangomessenger.tests.apis.users

import mangomessenger.bisunesslogic.data.TokenStorage
import mangomessenger.bisunesslogic.data.TokenStorageImpl
import mangomessenger.bisunesslogic.services.SignInService
import mangomessenger.bisunesslogic.services.SignInServiceImpl
import mangomessenger.core.apis.UsersApi
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.apis.requests.UpdateAccountRequest
import mangomessenger.tests.apis.asserts.MangoAsserts
import mangomessenger.tests.infrastructure.HttpPipelineFactoryImpl
import mangomessenger.tests.infrastructure.MangoApisFactoryImpl
import mangomessenger.tests.infrastructure.constants.EnvironmentVariables
import org.junit.Before
import org.junit.Test

class UpdateAccountTests {
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
    fun updateAccountSuccess() {
        val updateAccountRequest = UpdateAccountRequest(
            displayName = "TestDisplayName",
            birthdayDate = "1995-04-07T00:00:00Z",
            website = "test.com",
            username = "TestUserName",
            bio = "TestBio",
            address = "TestAddress"
        )
        val loginRequest = LoginRequest(
            EnvironmentVariables.testEmail(),
            EnvironmentVariables.testPassword())
        val responseTask = signInService
            .signIn(loginRequest)
            .thenCompose {
                usersApi.updateAccount(updateAccountRequest)
            }
            .thenApply { updateAccountResponse ->
                signInService.signOut()
                return@thenApply updateAccountResponse
            }
        val response = responseTask.get()
        MangoAsserts.assertSuccessResponse(response)
    }
}