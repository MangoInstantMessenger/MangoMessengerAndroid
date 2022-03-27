package mangomessenger.tests.apis.sessions

import mangomessenger.bisunesslogic.data.TokenStorage
import mangomessenger.bisunesslogic.data.TokenStorageImpl
import mangomessenger.bisunesslogic.services.SignInServiceImpl
import mangomessenger.core.apis.SessionsApi
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.tests.apis.asserts.MangoAsserts
import mangomessenger.tests.infrastructure.*
import mangomessenger.tests.infrastructure.constants.EnvironmentVariables
import mangomessenger.utils.UuidUtils
import org.junit.Before
import org.junit.Test

class DeleteCurrentSessionTests {
    private lateinit var tokenStorage: TokenStorage
    private lateinit var sessionsApi: SessionsApi

    @Before
    fun before() {
        tokenStorage = TokenStorageImpl()
        sessionsApi = MangoApisFactoryImpl(HttpPipelineFactoryImpl(tokenStorage)).createSessionsApi()
    }

    @Test
    fun deleteCurrentSessionsSuccess() {
        val signInService = SignInServiceImpl(sessionsApi, tokenStorage)
        val loginRequest = LoginRequest(EnvironmentVariables.testEmail(), EnvironmentVariables.testPassword())
        val responseTask = signInService.signIn(loginRequest).thenCompose {
            val refreshToken = tokenStorage.getTokens().refreshToken
            return@thenCompose sessionsApi.deleteCurrentSessionAsync(refreshToken)
        }
        val response = responseTask.get()
        MangoAsserts.assertSuccessResponse(response)
    }

    @Test
    fun deleteCurrentSessionUnauthorized() {
        val responseTask = sessionsApi.deleteCurrentSessionAsync(UuidUtils.emptyUUID())
        val response = responseTask.get()
        MangoAsserts.assertFailedResponse(response)
        MangoAsserts.assertUnauthorized(response)
    }
}