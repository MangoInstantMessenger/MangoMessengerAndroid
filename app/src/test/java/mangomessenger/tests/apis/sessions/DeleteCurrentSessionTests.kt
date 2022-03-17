package mangomessenger.tests.apis.sessions

import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.tests.apis.variables.Credentials
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class DeleteCurrentSessionTests {
    private lateinit var context: SessionsContext

    @Before
    fun before() {
        context = SessionsContext()
    }

    @Test
    fun deleteCurrentSessionSuccess() {
        val loginRequest = LoginRequest(Credentials.Email, Credentials.Password)
        val loginResponse = context.sessionsApi.loginAsync(loginRequest).thenApply {
            context.jwtToken = it.tokens?.accessToken.orEmpty()
            it
        }.get()
        val refreshToken = loginResponse.tokens?.refreshToken.toString()
        val response = context.sessionsApi.deleteCurrentSessionAsync(refreshToken).get()
        Assert.assertTrue(response.success)
    }

    @Test
    fun deleteCurrentSessionUnauthenticated() {
        val refreshToken = UUID.randomUUID().toString()
        val response = context.sessionsApi.deleteCurrentSessionAsync(refreshToken).get()
        Assert.assertFalse(response.success)
    }
}