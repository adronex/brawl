package by.brawl.ws.dto

import by.brawl.ws.holder.RoomHolder
import java.util.*

class BattlefieldDto(roomHolder: RoomHolder, receiverName: String) : AbstractDto(), JsonDto {

    val ownHeroes = roomHolder.battleHolder.ownHeroes(receiverName).map { HeroDto(it) }
    val enemyHeroes = roomHolder.battleHolder.enemyHeroes(receiverName).map { HeroDto(it) }
    val queue: Queue<HeroInQueueDto> = LinkedList()
    val currentStep = roomHolder.currentStep
    var battleLog = roomHolder.battleLog.map { StepLogDto(it, receiverName) }
    val gameState = roomHolder.gameState

    init {
        enemyHeroes.forEach { enemyHero ->
            run {
                val iterator = enemyHero.spells.iterator()
                while (iterator.hasNext()) {
                    val spellHolder = iterator.next()
                    val beenCasted = roomHolder.battleLog.any { stepLog -> stepLog.spellId == spellHolder.id && stepLog.casterId == enemyHero.id }
                    if (!beenCasted) iterator.remove()
                }
            }
        }

        roomHolder.queue.forEach { s ->
            val own = ownHeroes.any { it.id == s.id }
            val exposed = own || roomHolder.battleLog.any { stepLog -> stepLog.casterId == s.id }
            if (exposed) {
                queue.add(HeroInQueueDto(s.id, !own))
            } else {
                queue.add(HeroInQueueDto(null, !own))
            }
        }
    }

}