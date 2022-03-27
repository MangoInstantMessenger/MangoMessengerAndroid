package mangomessenger.tests.infrastructure

import mangomessenger.bisunesslogic.data.TokenStorage
import mangomessenger.core.apis.SessionsApi
import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.apis.responses.BaseResponse
import mangomessenger.core.apis.responses.LoginResponse
import mangomessenger.core.types.models.Tokens
import java.util.concurrent.CompletableFuture

class SignInService(
    private val sessionsApi: SessionsApi,
    private val tokenStorage: TokenStorage) {

    fun signIn(loginRequest: LoginRequest) : CompletableFuture<LoginResponse> {
        return sessionsApi.loginAsync(loginRequest).thenApply {
            val tokens = it.tokens ?: Tokens()
            tokenStorage.updateTokens(tokens)
            return@thenApply it
        }
    }

    fun signOut() : CompletableFuture<BaseResponse> {
        val refreshToken = tokenStorage.getTokens().refreshToken
        return sessionsApi.deleteCurrentSessionAsync(refreshToken).thenApply {
            tokenStorage.updateTokens(Tokens())
            return@thenApply it
        }
    }
}