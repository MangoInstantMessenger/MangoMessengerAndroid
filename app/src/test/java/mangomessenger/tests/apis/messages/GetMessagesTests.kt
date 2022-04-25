package mangomessenger.tests.apis.messages

import mangomessenger.bisunesslogic.data.TokenStorage
import mangomessenger.bisunesslogic.data.TokenStorageImpl
import mangomessenger.bisunesslogic.services.SignInService
import mangomessenger.bisunesslogic.services.SignInServiceImpl
import mangomessenger.core.apis.MessagesApi
import mangomessenger.core.apis.requests.GetMessagesRequest
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.tests.apis.asserts.MangoAsserts
import mangomessenger.tests.infrastructure.HttpPipelineFactoryImpl
import mangomessenger.tests.infrastructure.MangoApisFactoryImpl
import mangomessenger.tests.infrastructure.constants.EnvironmentVariables
import mangomessenger.utils.UuidUtils
import org.junit.Before
import org.junit.Test
import java.util.*

class GetMessagesTests {
    private lateinit var tokenStorage: TokenStorage
    private lateinit var signInService: SignInService
    private lateinit var messagesApi: MessagesApi

    @Before
    fun before() {
        tokenStorage = TokenStorageImpl()
        val httpPipelineFactory = HttpPipelineFactoryImpl(tokenStorage)
        val mangoApisFactory = MangoApisFactoryImpl(httpPipelineFactory)
        signInService = SignInServiceImpl(mangoApisFactory.createSessionsApi(), tokenStorage)
        messagesApi = mangoApisFactory.createMessagesApi()
    }

    @Test
    fun getMessagesSuccess() {
        val chatId = UUID.fromString("13d0ee43-460c-49ef-80d3-402103ec3445")
        val getMessagesRequest = GetMessagesRequest(chatId)
        val loginRequest = LoginRequest(EnvironmentVariables.testEmail(), EnvironmentVariables.testPassword())
        val responseTask = signInService
            .signIn(loginRequest)
            .thenCompose {
                messagesApi.getMessages(getMessagesRequest)
            }
            .thenApply { getMessagesResponse ->
                signInService.signOut()
                return@thenApply getMessagesResponse
            }
        val response = responseTask.get()
        MangoAsserts.assertSuccessResponse(response)
    }

    @Test
    fun getMessagesFailed() {
        val chatId = UuidUtils.emptyUUID()
        val getMessagesRequest = GetMessagesRequest(chatId)
        val loginRequest = LoginRequest(EnvironmentVariables.testEmail(), EnvironmentVariables.testPassword())
        val responseTask = signInService
            .signIn(loginRequest)
            .thenCompose {
                messagesApi.getMessages(getMessagesRequest)
            }
            .thenApply { getMessagesResponse ->
                signInService.signOut()
                return@thenApply getMessagesResponse
            }
        val response = responseTask.get()
        MangoAsserts.assertSuccessResponse(response)
    }
}