package mangomessenger.tests.apis.sessions

import mangomessenger.core.apis.factories.MangoApisFactory
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.http.pipelines.HttpPipelineFactoryDefault
import mangomessenger.tests.apis.asserts.MangoAsserts
import mangomessenger.tests.infrastructure.constants.EnvironmentVariables
import mangomessenger.tests.infrastructure.MangoApisFactoryImpl
import org.junit.Test

class LoginTests {
    private val mangoApisFactory: MangoApisFactory

    init {
        mangoApisFactory = MangoApisFactoryImpl(HttpPipelineFactoryDefault())
    }

    @Test
    fun loginSuccess() {
        val sessionsApi = mangoApisFactory.createSessionsApi()
        val loginRequest = LoginRequest(EnvironmentVariables.testEmail(), EnvironmentVariables.testPassword())
        val response = sessionsApi.loginAsync(loginRequest).get()
        MangoAsserts.assertSuccessResponse(response)
    }

    @Test
    fun loginInvalidCredentials() {
        val sessionApi = mangoApisFactory.createSessionsApi()
        val loginRequest = LoginRequest("invalid@email", "invalid@password")
        val response = sessionApi.loginAsync(loginRequest).get()
        MangoAsserts.assertFailedResponse(response)
        MangoAsserts.assertInvalidCredentials(response)
    }
}