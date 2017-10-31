package by.brawl.ws.dto

import by.brawl.ws.holder.RoomHolder
import java.util.*

class BattlefieldDto(roomHolder: RoomHolder, receiverName: String) : AbstractDto(), JsonDto {

    val myHeroes = roomHolder.battleHolder.myHeroes(receiverName).map { HeroDto(it) }
    val enemyHeroes = roomHolder.battleHolder.enemyHeroes(receiverName).map { HeroDto(it) }
    val queue: Queue<HeroInQueueDto> = LinkedList()
    val currentStep = roomHolder.currentStep
    var battleLog = roomHolder.battleLog.map { StepLogDto(it, receiverName) }
    val gameState = roomHolder.gameState

    init {
        myHeroes.forEach { it.position = it.position * (-1) - 1 }
        enemyHeroes.forEach { it.position++ }
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
//        roomHolder.getBattleHeroes(receiverName, true)
//                .forEach { heroHolder ->
//                    heroSpells.put(heroHolder.id,
//                                   heroHolder.allSpells
//                                           .filter { spellHolder ->
//                                               roomHolder
//                                                       .battleLog
//                                                       .any { stepLog ->
//                                                           stepLog.spellId == spellHolder
//                                                                   .id && stepLog.casterId == heroHolder
//                                                                   .id
//                                                       }
//                                           }
//                                           .map { SpellDto(it) }
//                                           .toSet())
//                }

        roomHolder.queue.forEach { s ->
            val my = myHeroes.any { it.id == s.id }
            val exposed = my || roomHolder.battleLog.any { stepLog -> stepLog.casterId == s.id }
            if (exposed) {
                queue.add(HeroInQueueDto(s.id, !my))
            } else {
                queue.add(HeroInQueueDto(null, !my))
            }
        }
    }

}