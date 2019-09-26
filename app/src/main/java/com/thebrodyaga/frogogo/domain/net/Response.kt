package com.thebrodyaga.frogogo.domain.net

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val id: Int,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("email") val email: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("created_at") val createdAt: String
) {

    fun equalsBy(
        firstName: String = "",
        lastName: String = "",
        email: String = ""
    ): Boolean = firstName == this.firstName && lastName == this.lastName && email == this.email
}