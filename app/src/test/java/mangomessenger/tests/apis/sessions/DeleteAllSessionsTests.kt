package mangomessenger.tests.apis.sessions

import mangomessenger.bisunesslogic.data.TokenStorage
import mangomessenger.bisunesslogic.data.TokenStorageImpl
import mangomessenger.core.apis.SessionsApi
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.tests.apis.asserts.MangoAsserts
import mangomessenger.tests.infrastructure.constants.EnvironmentVariables
import mangomessenger.tests.infrastructure.HttpPipelineFactoryImpl
import mangomessenger.tests.infrastructure.MangoApisFactoryImpl
import mangomessenger.bisunesslogic.services.SignInServiceImpl
import org.junit.Before
import org.junit.Test

class DeleteAllSessionsTests {
    private lateinit var tokenStorage: TokenStorage
    private lateinit var sessionsApi: SessionsApi

    @Before
    fun before() {
        tokenStorage = TokenStorageImpl()
        sessionsApi = MangoApisFactoryImpl(HttpPipelineFactoryImpl(tokenStorage)).createSessionsApi()
    }

    @Test
    fun deleteAllSessionsSuccess() {
        val signInService = SignInServiceImpl(sessionsApi, tokenStorage)
        val loginRequest = LoginRequest(EnvironmentVariables.testEmail(), EnvironmentVariables.testPassword())
        val responseTask = signInService.signIn(loginRequest).thenCompose {
            return@thenCompose sessionsApi.deleteAllSessionsAsync()
        }
        val response = responseTask.get()
        MangoAsserts.assertSuccessResponse(response)
    }

    @Test
    fun deleteAllSessionUnauthorized() {
        val responseTask = sessionsApi.deleteAllSessionsAsync()
        val response = responseTask.get()
        MangoAsserts.assertFailedResponse(response)
        MangoAsserts.assertUnauthorized(response)
    }
}