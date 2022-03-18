package com.stevesoltys.aidungeon

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.network.http.LoggingInterceptor
import com.stevesoltys.aidungeon.configuration.AIDungeonConfiguration
import com.stevesoltys.aidungeon.type.ActionInput
import com.stevesoltys.aidungeon.type.ActionInputType
import kotlinx.coroutines.runBlocking

class AIDungeon(
    private val configuration: AIDungeonConfiguration,
    private val apolloClient: ApolloClient = ApolloClient.Builder()
        .serverUrl(configuration.endpoint)
        .addHttpHeader("Content-Type", "application/json")
        .addHttpHeader("Authorization", "session ${configuration.token}")
        .addHttpInterceptor(LoggingInterceptor())
        .build()
) {

    companion object {
        private const val ALL_SCENARIOS_IDENTIFIER = "edd5fdc0-9c81-11ea-a76c-177e6c0711b5"
    }

    suspend fun getAllScenarios(): ScenarioContextGetScenarioQuery.Data {
        return getScenario(identifier = ALL_SCENARIOS_IDENTIFIER)
    }

    suspend fun getScenario(identifier: String): ScenarioContextGetScenarioQuery.Data {
        return apolloClient.query(
            ScenarioContextGetScenarioQuery(identifier = identifier)
        ).execute().data!!
    }

    suspend fun createAdventure(
        scenarioId: String,
        prompt: String,
        memory: String? = null
    ): ScenarioContextAddAdventureMutation.Data {

        return apolloClient.mutation(
            ScenarioContextAddAdventureMutation(
                scenarioId,
                prompt,
                Optional.presentIfNotNull(memory)
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

    suspend fun sendAction(actionInput: ActionInput): ActionInputMutation.Data {
        return apolloClient.mutation(
            ActionInputMutation(input = actionInput)
        ).execute().data!!
    }
}