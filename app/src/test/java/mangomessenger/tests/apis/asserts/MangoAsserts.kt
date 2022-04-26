package mangomessenger.tests.apis.asserts

import mangomessenger.core.apis.responses.BaseResponse
import mangomessenger.core.apis.responses.LoginResponse
import org.junit.Assert
import java.net.HttpURLConnection

class MangoAsserts {
    companion object {
        fun assertSuccessResponse(response: BaseResponse) {
            Assert.assertTrue(response.success)
        }

        fun assertFailedResponse(response: BaseResponse) {
            Assert.assertFalse(response.success)
        }

        fun assertInvalidCredentials(response: LoginResponse) {
            Assert.assertEquals("INVALID_CREDENTIALS", response.errorMessage)
        }

        fun assertUnauthorized(response: BaseResponse) {
            Assert.assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, response.statusCode)
        }

        fun assertImageUploadedSuccess(response: BaseResponse) {
            if (response.statusCode == 409) {
                Assert.assertEquals(response.errorMessage, "UPLOADED_DOCUMENTS_LIMIT_REACHED")
            }
            else {
                assertSuccessResponse(response)
            }
        }
    }
}