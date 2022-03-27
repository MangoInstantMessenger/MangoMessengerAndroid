package mangomessenger.tests.apis.sessions

import mangomessenger.bisunesslogic.data.TokenStorage
import mangomessenger.bisunesslogic.data.TokenStorageImpl
import mangomessenger.core.apis.SessionsApi
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.tests.apis.asserts.MangoAsserts
import mangomessenger.tests.infrastructure.*
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
        val signInService = SignInService(sessionsApi, tokenStorage)
        val loginRequest = LoginRequest(Constants.testEmail(), Constants.testPassword())
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