package mangomessenger.tests.apis.sessions

import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.tests.apis.variables.Credentials
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DeleteAllSessionsTests {
    private lateinit var context: SessionsContext

    @Before
    fun before() {
        context = SessionsContext()
    }

    @Test
    fun deleteSessionsSuccess() {
        val loginRequest = LoginRequest(Credentials.Email, Credentials.Password)
        context.sessionsApi.loginAsync(loginRequest).thenAccept {
             context.jwtToken = it.tokens?.accessToken.orEmpty()
        }.get()

        val response = context.sessionsApi.deleteAllSessionsAsync().get()
        Assert.assertTrue(response.success)
    }

    @Test
    fun deleteSessionsUnauthenticated() {
        val response = context.sessionsApi.deleteAllSessionsAsync().get()
        Assert.assertFalse(response.success)
        Assert.assertEquals(response.statusCode, 401)
    }
}