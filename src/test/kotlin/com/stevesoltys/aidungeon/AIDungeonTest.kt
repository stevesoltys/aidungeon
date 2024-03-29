package com.stevesoltys.aidungeon

import com.stevesoltys.aidungeon.type.ActionInput
import com.stevesoltys.aidungeon.type.ActionInputType
import com.stevesoltys.aidungeon.type.GameSettingsInput
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class AIDungeonTest {

    @Test
    fun `can start custom scenario session`(): Unit = runBlocking {
        val aiDungeon = AIDungeon()
        val scenarios = aiDungeon.getAllScenarios()
        val customScenario = scenarios.scenario!!.options!!.first { it!!.title == "custom" }!!

        aiDungeon.addDeviceToken()
        val user = aiDungeon.getUser()

        aiDungeon.updateGameSettings(
            GameSettingsInput(
                id = user.user!!.gameSettings.id.toString(),
                nsfwGeneration = true,
                unrestrictedInput = true
            )
        )

        val addAdventureResult = aiDungeon.createAdventure(
            scenarioId = customScenario.id.toString(),
            prompt = "You are Bob, a knight living in the kingdom of Larion."
        )

        val publicId = addAdventureResult.addAdventure!!.publicId!!

        aiDungeon.sendAction(
            ActionInput(
                publicId = publicId,
                text = "You are walking down the street when you see a man. He says 'Fuck off!'", // need to test profanity filter :)
                ttaiSupportedVersion = 3,
                type = ActionInputType.story
            )
        )

        val messages = aiDungeon.getMessages(publicId).adventure?.actionWindow
        assert(!messages.isNullOrEmpty())
    }
}