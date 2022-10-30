package com.stevesoltys.aidungeon.configuration

data class AIDungeonConfiguration(
    val aiDungeonCredentials: AIDungeonCredentials? = null,
    val endpoint: String = "https://api.aidungeon.io/graphql",
    val firebaseApiKey: String = "AIzaSyCnvo_XFPmAabrDkOKBRpbivp5UH8r_3mg"
)

data class AIDungeonCredentials(
    val username: String? = null,
    val password: String? = null
)