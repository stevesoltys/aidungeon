package com.stevesoltys.aidungeon.configuration

data class AIDungeonConfiguration(
    val token: String,
    val endpoint: String = "https://api.aidungeon.io/graphql"
)