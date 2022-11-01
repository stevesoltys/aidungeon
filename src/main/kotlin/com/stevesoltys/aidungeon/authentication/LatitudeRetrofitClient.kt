package com.stevesoltys.aidungeon.authentication

import com.stevesoltys.aidungeon.authentication.dto.LatitudeAuthResponse
import retrofit2.http.Headers
import retrofit2.http.POST

interface LatitudeRetrofitClient {

    /**
     * Authenticates anonymously.
     */
    @POST("user/auth/v2/anonymous")
    suspend fun anonymous(): LatitudeAuthResponse
}
