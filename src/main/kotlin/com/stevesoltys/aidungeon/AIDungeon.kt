package com.stevesoltys.aidungeon

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.http.LoggingInterceptor
import com.stevesoltys.aidungeon.authentication.FirebaseAuthClient
import com.stevesoltys.aidungeon.authentication.LatitudeAuthClient
import com.stevesoltys.aidungeon.configuration.AIDungeonConfiguration
import com.stevesoltys.aidungeon.type.ActionInput
import com.stevesoltys.aidungeon.type.GameSettingsInput
import kotlinx.coroutines.runBlocking

class AIDungeon(configuration: AIDungeonConfiguration = AIDungeonConfiguration()) {

    companion object {
        private const val ALL_SCENARIOS_IDENTIFIER = "edd5fdc0-9c81-11ea-a76c-177e6c0711b5"
    }

    private val token: String

    private val apolloClient: ApolloClient

    init {
        // TODO: Fetch new access token using refresh token on expiry
        token = runBlocking {
            if (configuration.aiDungeonCredentials == null) {
                val user = LatitudeAuthClient().authenticateAnonymously()
                "session ${user.accessToken}"
            } else {
                val user = FirebaseAuthClient(
                    firebaseApiKey = configuration.firebaseApiKey
                ).authenticate(configuration.aiDungeonCredentials)
                "firebase ${user.idToken}"
            }
        }

        apolloClient = ApolloClient.Builder()
            .serverUrl(configuration.endpoint)
            .addHttpHeader("Content-Type", "application/json")
            .addHttpHeader("Authorization", token)
            .addHttpInterceptor(LoggingInterceptor())
            .build()
    }

    suspend fun getAllScenarios(): ScenarioContextGetScenarioQuery.Data {
        return getScenario(identifier = ALL_SCENARIOS_IDENTIFIER)
    }

    suspend fun addDeviceToken(token: String = "web", platform: String = "web") {
        apolloClient.mutation(
            NotificationsWebAddDeviceTokenMutation(token, platform)
        ).execute().data!!
    }

    suspend fun getScenario(identifier: String): ScenarioContextGetScenarioQuery.Data {
        return apolloClient.query(
            ScenarioContextGetScenarioQuery(identifier = identifier)
        ).execute().data!!
    }

    suspend fun getUser(): UserContextGetUserQuery.Data {
        return apolloClient.query(
            UserContextGetUserQuery()
        ).execute().data!!
    }

    suspend fun createAdventure(
        scenarioId: String,
        prompt: String,
        memory: String = "",
        authorsNote: String = ""
    ): ScenarioStartScreenAddAdventureMutation.Data {

        return apolloClient.mutation(
            ScenarioStartScreenAddAdventureMutation(
                scenarioId,
                prompt,
                memory,
                authorsNote
            )
        ).execute().data!!
    }

    suspend fun getAdventure(publicId: String): AdventureContextGetAdventureQuery.Data {
        return apolloClient.query(
            AdventureContextGetAdventureQuery(
                publicId = publicId
            )
        ).execute().data!!
    }

    suspend fun getMessages(publicId: String): ActionSubscriptionGetContentQuery.Data {
        return apolloClient.query(
            ActionSubscriptionGetContentQuery(
                publicId = publicId
            )
        ).execute().data!!
    }

    suspend fun updateGameSettings(
        gameSettingsInput: GameSettingsInput
    ): SettingsScreenSaveSettingsMobileMutation.Data {
        return apolloClient.mutation(
            SettingsScreenSaveSettingsMobileMutation(input = gameSettingsInput)
        ).execute().data!!
    }

    suspend fun increaseActionsBalance(): IncreaseActionsBalanceAdContextMutation.Data {
        return apolloClient.mutation(
            IncreaseActionsBalanceAdContextMutation()
        ).execute().data!!
    }

    suspend fun sendAction(actionInput: ActionInput): ActionContextAddActionMutation.Data {
        return apolloClient.mutation(
            ActionContextAddActionMutation(input = actionInput)
        ).execute().data!!
    }
}