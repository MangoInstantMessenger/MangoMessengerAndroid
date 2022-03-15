package mangomessenger.core.apis

import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.apis.responses.BaseResponse
import mangomessenger.core.apis.responses.LoginResponse
import java.util.concurrent.CompletableFuture

interface SessionsApi {

    fun loginAsync(request: LoginRequest): CompletableFuture<LoginResponse>

    fun refreshSessionAsync(refreshToken: String): CompletableFuture<LoginResponse>

    fun deleteCurrentSessionAsync(refreshToken: String): CompletableFuture<BaseResponse>

    fun deleteAllSessionsAsync(): CompletableFuture<BaseResponse>
}