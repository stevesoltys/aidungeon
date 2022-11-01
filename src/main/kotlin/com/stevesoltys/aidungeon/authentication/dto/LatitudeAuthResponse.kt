package com.stevesoltys.aidungeon.authentication.dto

import com.google.gson.annotations.SerializedName

data class LatitudeAuthResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    val user: LatitudeUser
)