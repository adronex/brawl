package by.brawl.ws.dto

import by.brawl.ws.holder.BattlefieldHolder
import java.util.*

class BattlefieldDto(battlefieldHolder: BattlefieldHolder, receiverName: String) : AbstractDto(), JsonDto {

    val myHeroes = battlefieldHolder.getBattleHeroes(receiverName, false).map { HeroDto(it) }
    val enemyHeroes = battlefieldHolder.getBattleHeroes(receiverName, true).map { HeroDto(it) }
    val heroSpells = battlefieldHolder.getBattleHeroes(receiverName, false)
            .associateBy({ it.id }, { it.allSpells.map { SpellDto(it) }.toSet() }).toMutableMap()
    val queue: Queue<HeroInQueueDto> = LinkedList()
    val currentStep = battlefieldHolder.currentStep
    var battleLog = battlefieldHolder.battleLog.map { StepLogDto(it, receiverName) }
    val gameState = battlefieldHolder.gameState

    init {
        // todo: refactor
        battlefieldHolder.getBattleHeroes(receiverName, true)
                .forEach { heroHolder ->
                    heroSpells.put(heroHolder.id,
                            heroHolder.allSpells
                                    .filter { spellHolder ->
                                        battlefieldHolder
                                                .battleLog
                                                .count { stepLog ->
                                                    stepLog.spellId == spellHolder
                                                            .id && stepLog.casterId == heroHolder
                                                            .id
                                                } > 0
                                    }
                                    .map { SpellDto(it) }
                                    .toSet())
                }

        val myHeroesIds = myHeroes.map { it.id }

        battlefieldHolder.queue.forEach { s ->
            val enemy = !myHeroesIds.contains(s.id)
            val exposed = !enemy || battlefieldHolder.battleLog.count { stepLog -> stepLog.casterId == s.id } > 0
            if (exposed) {
                queue.add(HeroInQueueDto(s.id, enemy))
            } else {
                queue.add(HeroInQueueDto(null, enemy))
            }
        }
    }

}