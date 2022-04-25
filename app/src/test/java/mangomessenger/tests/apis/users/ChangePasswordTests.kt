package mangomessenger.tests.apis.users

import mangomessenger.bisunesslogic.data.TokenStorage
import mangomessenger.bisunesslogic.data.TokenStorageImpl
import mangomessenger.bisunesslogic.services.SignInService
import mangomessenger.bisunesslogic.services.SignInServiceImpl
import mangomessenger.core.apis.UsersApi
import mangomessenger.core.apis.requests.ChangePasswordRequest
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.apis.responses.BaseResponse
import mangomessenger.tests.apis.asserts.MangoAsserts
import mangomessenger.tests.infrastructure.HttpPipelineFactoryImpl
import mangomessenger.tests.infrastructure.MangoApisFactoryImpl
import mangomessenger.tests.infrastructure.constants.EnvironmentVariables
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CompletableFuture
import kotlin.math.sign

class ChangePasswordTests {
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
    fun changePasswordSuccess() {
        val changePasswordRequest = ChangePasswordRequest(
            EnvironmentVariables.testPassword(),
            EnvironmentVariables.testNewPassword(),
            EnvironmentVariables.testNewPassword()
        )
        val loginRequest = LoginRequest(
            email = EnvironmentVariables.testEmail(),
            password = EnvironmentVariables.testPassword()
        )
        val responseTask = signInService
            .signIn(loginRequest)
            .thenCompose {
                usersApi.changePassword(changePasswordRequest)
            }
            .thenApply { changePasswordResponse ->
                rollbackPassword()
                return@thenApply changePasswordResponse
            }
            .thenApply { changePasswordResponse ->
                signInService.signOut()
                return@thenApply changePasswordResponse
            }
        val response = responseTask.get()
        MangoAsserts.assertSuccessResponse(response)
    }

    private fun rollbackPassword(): CompletableFuture<BaseResponse> {
        val changePasswordRequest = ChangePasswordRequest(
            EnvironmentVariables.testNewPassword(),
            EnvironmentVariables.testPassword(),
            EnvironmentVariables.testPassword()
        )
        return usersApi.changePassword(changePasswordRequest)
    }
}