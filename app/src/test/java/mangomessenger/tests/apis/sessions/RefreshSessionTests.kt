package mangomessenger.tests.apis.sessions

import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.tests.apis.variables.Credentials
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class RefreshSessionTests {
    private lateinit var context: SessionsContext

    @Before
    fun before() {
        context = SessionsContext()
    }

    @Test
    fun refreshSessionSuccess() {
        val loginRequest = LoginRequest(Credentials.Email, Credentials.Password)
        val loginResponse = context.sessionsApi.loginAsync(loginRequest).thenApply {
            context.jwtToken = it.tokens?.accessToken.orEmpty()
            it
        }.get()
        val refreshToken = loginResponse.tokens?.refreshToken.toString()
        val response = context.sessionsApi.refreshSessionAsync(refreshToken).get()
        Assert.assertTrue(response.success)
    }

    @Test
    fun refreshSessionUnauthenticated() {
        val refreshToken = UUID.randomUUID().toString()
        val response = context.sessionsApi.refreshSessionAsync(refreshToken).get()
        Assert.assertFalse(response.success)
    }
}