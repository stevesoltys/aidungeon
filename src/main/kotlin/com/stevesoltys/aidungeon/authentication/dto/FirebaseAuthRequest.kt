package com.stevesoltys.aidungeon.authentication.dto

data class FirebaseAuthRequest(
    var email: String? = null,
    var password: String? = null,
    var returnSecureToken: Boolean = true
)