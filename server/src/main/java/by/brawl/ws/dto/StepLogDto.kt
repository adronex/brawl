package by.brawl.ws.dto

import by.brawl.ws.holder.StepLogHolder

import java.util.Objects

class StepLogDto(stepLogHolder: StepLogHolder, receiverId: String) {
    val spellId = stepLogHolder.spellId
    val senderId = stepLogHolder.casterId
    val enemy = receiverId != stepLogHolder.senderId
}
