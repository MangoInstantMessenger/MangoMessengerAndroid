package mangomessenger.core.apis

import mangomessenger.core.apis.requests.ChangePasswordRequest
import mangomessenger.core.apis.requests.RegisterRequest
import mangomessenger.core.apis.requests.UpdateAccountRequest
import mangomessenger.core.apis.requests.UpdateSocialsRequest
import mangomessenger.core.apis.responses.BaseResponse
import mangomessenger.core.apis.responses.GetUserResponse
import mangomessenger.core.apis.responses.UpdateProfilePictureResponse
import java.io.File
import java.util.*
import java.util.concurrent.CompletableFuture

interface UsersApi {
    /**
     * Registers user in the system. Then sends verification email.
     */
    fun register(registerRequest: RegisterRequest): CompletableFuture<BaseResponse>

    /**
     * Changes password by current password.
     */
    fun changePassword(changePasswordRequest: ChangePasswordRequest): CompletableFuture<BaseResponse>

    /**
     * Gets user by ID.
     */
    fun getUser(userId: UUID): CompletableFuture<GetUserResponse>

    /**
     * Updates user's social network user names.
     */
    fun updateSocials(updateSocialsRequest: UpdateSocialsRequest): CompletableFuture<BaseResponse>

    /**
     * Updates user's personal account information.
     */
    fun updateAccount(updateAccountRequest: UpdateAccountRequest): CompletableFuture<BaseResponse>

    /**
     * Updates user's profile picture. Accepted formats .JPG, .PNG with size up to 2MB.
     */
    fun updateProfilePicture(picture: File): CompletableFuture<UpdateProfilePictureResponse>
}