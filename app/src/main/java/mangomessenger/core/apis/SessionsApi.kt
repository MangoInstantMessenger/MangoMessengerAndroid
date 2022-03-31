package mangomessenger.core.apis

import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.apis.responses.BaseResponse
import mangomessenger.core.apis.responses.LoginResponse
import java.util.*
import java.util.concurrent.CompletableFuture

interface SessionsApi {

    fun loginAsync(request: LoginRequest): CompletableFuture<LoginResponse>

    fun refreshSessionAsync(refreshToken: UUID): CompletableFuture<LoginResponse>

    fun deleteCurrentSessionAsync(refreshToken: UUID): CompletableFuture<BaseResponse>

    fun deleteAllSessionsAsync(): CompletableFuture<BaseResponse>
}