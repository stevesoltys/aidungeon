package com.stevesoltys.aidungeon.authentication

import com.stevesoltys.aidungeon.authentication.dto.FirebaseAuthRequest
import com.stevesoltys.aidungeon.authentication.dto.FirebaseAuthResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface FirebaseRetrofitClient {

    /**
     * Authenticates using a username and password.
     */
    @POST("v1/accounts:signInWithPassword")
    @Headers("origin: https://play.aidungeon.io")
    suspend fun signInWithPassword(
        @Query("key") firebaseApiKey: String,
        @Body request: FirebaseAuthRequest
    ): FirebaseAuthResponse
}
