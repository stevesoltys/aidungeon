package com.stevesoltys.aidungeon.authentication

import com.stevesoltys.aidungeon.authentication.dto.LatitudeAuthResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LatitudeAuthClient(baseUrl: String = BASE_URL) {

    companion object {
        const val BASE_URL = "https://api.latitude.io/"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val authClient = retrofit.create(LatitudeRetrofitClient::class.java)

    suspend fun authenticateAnonymously(): LatitudeAuthResponse {
        return authClient.anonymous()
    }
}