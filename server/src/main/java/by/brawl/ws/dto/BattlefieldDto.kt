package by.brawl.ws.dto

import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.holder.GameState

import java.util.*

class BattlefieldDto(battlefieldHolder: BattlefieldHolder, receiverName: String) : AbstractDto(), JsonDto {

    var myHeroes: List<HeroDto> = ArrayList()
    var enemyHeroes: List<HeroDto> = ArrayList()
    private val heroSpells = HashMap<String, Set<SpellDto>>()
    val queue: Queue<HeroInQueueDto> = LinkedList()
    val currentStep: Int?
    var battleLog: List<StepLogDto> = ArrayList()
    val gameState: GameState

    init {

        myHeroes = battlefieldHolder.getBattleHeroes(receiverName, false).map { HeroDto(it) }
        enemyHeroes = battlefieldHolder.getBattleHeroes(receiverName, true).map { HeroDto(it) }

        battlefieldHolder.getBattleHeroes(receiverName, false)
                .forEach {
                    heroHolder -> heroSpells.put(heroHolder.id,heroHolder.allSpells.map { SpellDto(it) }.toSet())
                }

        // todo: refactor
        battlefieldHolder.getBattleHeroes(receiverName, true)
                .forEach { heroHolder ->
                    heroSpells.put(heroHolder.id,
                            heroHolder.allSpells
                                    .filter { spellHolder ->
                                        battlefieldHolder
                                                .battleLog
                                                .stream()
                                                .filter { stepLog ->
                                                    stepLog.spellId == spellHolder
                                                            .id && stepLog.casterId == heroHolder
                                                            .id
                                                }
                                                .count() > 0
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

        currentStep = battlefieldHolder.currentStep
        battleLog = battlefieldHolder.battleLog.map { StepLogDto(it, receiverName) }
        gameState = battlefieldHolder.gameState
    }

    fun getHeroSpells(): Map<String, Set<SpellDto>> {
        return heroSpells
    }
}
