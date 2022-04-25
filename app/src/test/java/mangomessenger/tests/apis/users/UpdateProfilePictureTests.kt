package mangomessenger.tests.apis.users

import mangomessenger.bisunesslogic.data.TokenStorage
import mangomessenger.bisunesslogic.data.TokenStorageImpl
import mangomessenger.bisunesslogic.services.SignInService
import mangomessenger.bisunesslogic.services.SignInServiceImpl
import mangomessenger.core.apis.UsersApi
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.tests.apis.asserts.MangoAsserts
import mangomessenger.tests.infrastructure.HttpPipelineFactoryImpl
import mangomessenger.tests.infrastructure.MangoApisFactoryImpl
import mangomessenger.tests.infrastructure.constants.EnvironmentVariables
import org.junit.Before
import org.junit.Test
import java.io.File

class UpdateProfilePictureTests {
    private lateinit var tokenStorage: TokenStorage
    private lateinit var usersApi: UsersApi
    private lateinit var signInService: SignInService

    @Before
    fun before() {
        tokenStorage = TokenStorageImpl()
        val pipelineFactory = HttpPipelineFactoryImpl(tokenStorage)
        val mangoApisFactory = MangoApisFactoryImpl(pipelineFactory)
        usersApi = mangoApisFactory.createUsersApi()
        signInService = SignInServiceImpl(mangoApisFactory.createSessionsApi(), tokenStorage)
    }

    @Test
    fun updateProfilePictureSuccess() {
        val fileUrl = javaClass.classLoader?.getResource("floppa.jpg")
        val file = File(fileUrl?.file ?: throw NullPointerException("'fileUrl' was null."))
        val loginRequest = LoginRequest(
            EnvironmentVariables.testEmail(),
            EnvironmentVariables.testPassword())
        val responseTask = signInService
            .signIn(loginRequest)
            .thenCompose {
                println(it)
                usersApi.updateProfilePicture(file)
            }
        val response = responseTask.get()
        println("message: ${response.message}")
        println("errorMessage: ${response.errorMessage}")
        println("statusCode: ${response.statusCode}")
        println("newUserPictureUrl: ${response.newUserPictureUrl}")
        println("errorDetails: ${response.errorDetails}")
        println("success: ${response.success}")
        MangoAsserts.assertSuccessResponse(response)
    }
}