package mangomessenger.tests.apis.users

import mangomessenger.bisunesslogic.data.TokenStorage
import mangomessenger.bisunesslogic.data.TokenStorageImpl
import mangomessenger.core.apis.UsersApi
import mangomessenger.core.apis.requests.RegisterRequest
import mangomessenger.tests.apis.asserts.MangoAsserts
import mangomessenger.tests.infrastructure.HttpPipelineFactoryImpl
import mangomessenger.tests.infrastructure.MangoApisFactoryImpl
import mangomessenger.tests.infrastructure.constants.EnvironmentVariables
import org.junit.Before
import org.junit.Test

class RegisterTests {
    private lateinit var tokenStorage: TokenStorage
    private lateinit var usersApi: UsersApi

    @Before
    fun before() {
        tokenStorage = TokenStorageImpl()
        val pipelineFactory = HttpPipelineFactoryImpl(tokenStorage)
        val mangoApisFactory = MangoApisFactoryImpl(pipelineFactory)
        usersApi = mangoApisFactory.createUsersApi()
    }

    // TODO: Add some feature to remove created user from DB after test.
    @Test
    fun registerSuccess() {
        val registerRequest = RegisterRequest(
            displayName = "TestUser",
            email = EnvironmentVariables.registerEmail(),
            password = "MyStrong!Passw0rd",
            termsAccepted = true
        )
        val responseTask = usersApi.register(registerRequest)
        val response = responseTask.get()
        MangoAsserts.assertSuccessResponse(response)
    }

    @Test
    fun registerFailed() {
        val registerRequest = RegisterRequest(
            displayName = "TestUser",
            email = "invalid_email",
            password = "invalid_password",
            termsAccepted = true
        )
        val responseTask = usersApi.register(registerRequest)
        val response = responseTask.get()
        MangoAsserts.assertFailedResponse(response)
    }
}