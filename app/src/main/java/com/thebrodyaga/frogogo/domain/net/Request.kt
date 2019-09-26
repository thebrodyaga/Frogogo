package com.thebrodyaga.frogogo.domain.net

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("email") val email: String,
    @SerializedName("avatar_url") val avatarUrl: String
)