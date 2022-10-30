package com.stevesoltys.aidungeon.authentication.dto

import com.google.gson.annotations.SerializedName

data class FirebaseAuthResponse(
    val kind: String,
    @SerializedName("localId") val localID: String,
    val email: String,
    val displayName: String,
    val idToken: String,
    val registered: Boolean,
    val refreshToken: String,
    val expiresIn: String
)