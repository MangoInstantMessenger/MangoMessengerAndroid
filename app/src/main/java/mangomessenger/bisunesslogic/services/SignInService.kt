package mangomessenger.bisunesslogic.services

import mangomessenger.core.apis.requests.LoginRequest
import mangomessenger.core.apis.responses.BaseResponse
import mangomessenger.core.apis.responses.LoginResponse
import java.util.concurrent.CompletableFuture

interface SignInService {
    /**
     * SignIn and store current session.
     */
    fun signIn(loginRequest: LoginRequest) : CompletableFuture<LoginResponse>

    /**
     * SignOut and remove current session.
     */
    fun signOut() : CompletableFuture<BaseResponse>

    /**
     * SignOut from all devices and clear sessions.
     */
    fun signOutAll() : CompletableFuture<BaseResponse>
}