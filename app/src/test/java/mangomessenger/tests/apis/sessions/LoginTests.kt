package mangomessenger.tests.apis.sessions

import mangomessenger.core.apis.SessionsApi
import mangomessenger.core.apis.SessionsApiImpl
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.http.HttpClient
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import mangomessenger.tests.apis.variables.Credentials

class LoginTests {
    private lateinit var sessionsApi: SessionsApi

    @Before
    fun before() {
        sessionsApi = SessionsApiImpl(HttpClient(emptyList()))
    }

    @Test
    fun loginSuccess() {
        val request = LoginRequest(Credentials.Email, Credentials.Password)
        val response = sessionsApi.loginAsync(request).get()
        Assert.assertTrue(response.success)
    }

    @Test
    fun loginInvalidCredentials() {
        val request = LoginRequest("example@gmail.com", "password")
        val response = sessionsApi.loginAsync(request).get()
        Assert.assertFalse(response.success)
        Assert.assertEquals(response.errorMessage, "INVALID_CREDENTIALS")
    }

    @Test
    fun loginWithEmptyFields() {
        val request = LoginRequest("", "")
        val response = sessionsApi.loginAsync(request).get()
        Assert.assertFalse(response.success)
    }
}