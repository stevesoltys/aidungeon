package com.stevesoltys.aidungeon.authentication

import com.stevesoltys.aidungeon.authentication.dto.FirebaseAuthRequest
import com.stevesoltys.aidungeon.authentication.dto.FirebaseAuthResponse
import com.stevesoltys.aidungeon.configuration.AIDungeonCredentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FirebaseAuthClient(
    baseUrl: String = BASE_URL,
    private val firebaseApiKey: String
) {

    companion object {
        const val BASE_URL = "https://identitytoolkit.googleapis.com/"
    }

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
        )
        .build()

    private val authClient = retrofit.create(FirebaseRetrofitClient::class.java)

    suspend fun authenticate(aiDungeonCredentials: AIDungeonCredentials): FirebaseAuthResponse {
        return authClient.signInWithPassword(
            firebaseApiKey = firebaseApiKey,
            request = FirebaseAuthRequest(
                email = aiDungeonCredentials.username!!,
                password = aiDungeonCredentials.password!!,
                returnSecureToken = true
            )
        )
    }
}