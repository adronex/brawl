package by.brawl.ws.dto

import by.brawl.ws.holder.BattlefieldHolder
import java.util.*

class BattlefieldDto(battlefieldHolder: BattlefieldHolder, receiverName: String) : AbstractDto(), JsonDto {

    val myHeroes = battlefieldHolder.getBattleHeroes(receiverName, false).map { HeroDto(it) }
    val enemyHeroes = battlefieldHolder.getBattleHeroes(receiverName, true).map { HeroDto(it) }
    val queue: Queue<HeroInQueueDto> = LinkedList()
    val currentStep = battlefieldHolder.currentStep
    var battleLog = battlefieldHolder.battleLog.map { StepLogDto(it, receiverName) }
    val gameState = battlefieldHolder.gameState

    init {
        enemyHeroes.forEach { enemyHero ->
            run {
                val iterator = enemyHero.spells.iterator()
                while (iterator.hasNext()) {
                    val spellHolder = iterator.next()
                    val beenCasted = battlefieldHolder.battleLog.any { stepLog -> stepLog.spellId == spellHolder.id && stepLog.casterId == enemyHero.id }
                    if (!beenCasted) iterator.remove()
                }
            }
        }
//        battlefieldHolder.getBattleHeroes(receiverName, true)
//                .forEach { heroHolder ->
//                    heroSpells.put(heroHolder.id,
//                                   heroHolder.allSpells
//                                           .filter { spellHolder ->
//                                               battlefieldHolder
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

        battlefieldHolder.queue.forEach { s ->
            val my = myHeroes.any { it.id == s.id }
            val exposed = my || battlefieldHolder.battleLog.any { stepLog -> stepLog.casterId == s.id }
            if (exposed) {
                queue.add(HeroInQueueDto(s.id, !my))
            } else {
                queue.add(HeroInQueueDto(null, !my))
            }
        }
    }

}