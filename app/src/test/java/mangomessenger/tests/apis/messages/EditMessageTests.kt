package mangomessenger.tests.apis.messages

import mangomessenger.bisunesslogic.data.TokenStorage
import mangomessenger.bisunesslogic.data.TokenStorageImpl
import mangomessenger.bisunesslogic.services.SignInService
import mangomessenger.bisunesslogic.services.SignInServiceImpl
import mangomessenger.core.apis.MessagesApi
import mangomessenger.core.apis.requests.EditMessageRequest
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.apis.responses.EditMessageResponse
import mangomessenger.tests.apis.asserts.MangoAsserts
import mangomessenger.tests.infrastructure.HttpPipelineFactoryImpl
import mangomessenger.tests.infrastructure.MangoApisFactoryImpl
import mangomessenger.tests.infrastructure.constants.EnvironmentVariables
import mangomessenger.utils.UuidUtils
import org.junit.Before
import org.junit.Test
import java.util.*
import java.util.concurrent.CompletableFuture

class EditMessageTests {
    @Test
    fun editMessageSuccess() {
        val tokenStorage = TokenStorageImpl()
        val httpPipelineFactory = HttpPipelineFactoryImpl(tokenStorage)
        val mangoApisFactory = MangoApisFactoryImpl(httpPipelineFactory)
        val signInService = SignInServiceImpl(mangoApisFactory.createSessionsApi(), tokenStorage)
        val messagesApi = mangoApisFactory.createMessagesApi()
        val chatId = UUID.fromString("13d0ee43-460c-49ef-80d3-402103ec3445")
        val messageId = UUID.fromString("61a5b58b-8217-40cc-9bac-22e368ff2d54")
        val editMessageRequest = EditMessageRequest(
            messageId,
            "Hello, World [UnitTestMessageEdited]",
            chatId)
        val loginRequest = LoginRequest(EnvironmentVariables.testEmail(), EnvironmentVariables.testPassword())
        println("loginRequest: $loginRequest")
        val responseTask = signInService
                .signIn(loginRequest)
                .thenCompose {
                    println(it)
                    messagesApi.editMessage(editMessageRequest)
                }
        val response = responseTask.get()
        println("message: ${response.message}")
        println("errorMessage: ${response.errorMessage}")
        println("statusCode: ${response.statusCode}")
        println("errorDetails: ${response.errorDetails}")
        println("success: ${response.success}")
        MangoAsserts.assertSuccessResponse(response)
    }

    @Test
    fun editMessageFailed() {
        val tokenStorage = TokenStorageImpl()
        val httpPipelineFactory = HttpPipelineFactoryImpl(tokenStorage)
        val mangoApisFactory = MangoApisFactoryImpl(httpPipelineFactory)
        val signInService = SignInServiceImpl(mangoApisFactory.createSessionsApi(), tokenStorage)
        val messagesApi = mangoApisFactory.createMessagesApi()
        val chatId = UuidUtils.emptyUUID()
        val messageId = UuidUtils.emptyUUID()
        val editMessageRequest = EditMessageRequest(
            messageId,
            "Hello, World [UnitTestMessageEdited]",
            chatId)
        val loginRequest = LoginRequest(EnvironmentVariables.testEmail(), EnvironmentVariables.testPassword())
        println("loginRequest: $loginRequest")
        val responseTask =  signInService
                .signIn(loginRequest)
                .thenCompose {
                    println(it)
                    messagesApi.editMessage(editMessageRequest)
                }
        val response = responseTask.get()
        MangoAsserts.assertFailedResponse(response)
    }
}