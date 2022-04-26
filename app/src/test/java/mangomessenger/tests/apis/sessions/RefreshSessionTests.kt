package mangomessenger.tests.apis.sessions

import mangomessenger.core.apis.SessionsApi
import mangomessenger.core.apis.factories.MangoApisFactory
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.http.pipelines.HttpPipelineFactoryDefault
import mangomessenger.tests.apis.asserts.MangoAsserts
import mangomessenger.tests.infrastructure.constants.EnvironmentVariables
import mangomessenger.tests.infrastructure.MangoApisFactoryImpl
import mangomessenger.utils.UuidUtils
import org.junit.Before
import org.junit.Test

class RefreshSessionTests {
    private lateinit var mangoApisFactory: MangoApisFactory
    private lateinit var sessionsApi: SessionsApi

    @Before
    fun before() {
        mangoApisFactory = MangoApisFactoryImpl(HttpPipelineFactoryDefault())
        sessionsApi = mangoApisFactory.createSessionsApi()
    }

    @Test
    fun refreshSessionSuccess() {
        val loginRequest = LoginRequest(EnvironmentVariables.testEmail(), EnvironmentVariables.testPassword())
        val responseTask = sessionsApi
            .loginAsync(loginRequest)
            .thenCompose {
                val refreshToken = it.tokens?.refreshToken ?: UuidUtils.emptyUUID()
                return@thenCompose sessionsApi.refreshSessionAsync(refreshToken)
            }
        val response = responseTask.get()
        MangoAsserts.assertSuccessResponse(response)
    }

    @Test
    fun refreshSessionFailed() {
        val responseTask = sessionsApi.refreshSessionAsync(UuidUtils.emptyUUID())
        val response = responseTask.get()
        MangoAsserts.assertFailedResponse(response)
    }
}