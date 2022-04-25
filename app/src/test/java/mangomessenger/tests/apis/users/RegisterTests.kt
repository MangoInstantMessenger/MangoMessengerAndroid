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
import java.util.*

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

    @Test
    fun registerSuccess() {
        val randomEmail = UUID.randomUUID().toString() + "@gmail.com"
        val registerRequest = RegisterRequest(
            displayName = "TestUser",
            email = randomEmail,
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