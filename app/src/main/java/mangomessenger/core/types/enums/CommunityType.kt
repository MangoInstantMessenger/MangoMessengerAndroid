package mangomessenger.core.types.enums

import com.google.gson.annotations.SerializedName

enum class CommunityType {
    @SerializedName("1")
    DirectChat,
    @SerializedName("2")
    PublicChannel;
}