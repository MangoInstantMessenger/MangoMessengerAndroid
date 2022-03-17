package mangomessenger.tests.apis.sessions

import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.tests.apis.variables.Credentials
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LoginTests {
    private lateinit var context: SessionsContext

    @Before
    fun before() {
        context = SessionsContext()
    }

    @Test
    fun loginSuccess() {
        val request = LoginRequest(Credentials.Email, Credentials.Password)
        val response = context.sessionsApi.loginAsync(request).get()
        Assert.assertTrue(response.success)
    }

    @Test
    fun loginInvalidCredentials() {
        val request = LoginRequest("example@gmail.com", "password")
        val response = context.sessionsApi.loginAsync(request).get()
        Assert.assertFalse(response.success)
        Assert.assertEquals(response.errorMessage, "INVALID_CREDENTIALS")
    }

    @Test
    fun loginWithEmptyFields() {
        val request = LoginRequest("", "")
        val response = context.sessionsApi.loginAsync(request).get()
        Assert.assertFalse(response.success)
    }
}