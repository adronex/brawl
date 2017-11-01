package by.brawl.ws.huihui

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder
import by.brawl.ws.holder.gamesession.GameSession
import by.brawl.ws.huihui.conf.ImpactType
import by.brawl.ws.huihui.conf.IntegerImpactConfig
import by.brawl.ws.huihui.conf.SpellConfig
import by.brawl.ws.huihui.handlers.CheckEndGameHandler
import by.brawl.ws.utils.Calculator
import org.springframework.stereotype.Service

@Service
class SpellService(private val preconditionsPool: HandlersPool,
                   private val checkEndGameHandler: CheckEndGameHandler) {

    fun cast(gameSession: GameSession,
             spellId: String,
             clickedHeroPosition: Int,
             targetEnemy: Boolean) {
        val roomHolder = gameSession.roomHolder
        val caster: HeroHolder = roomHolder.getFirstHeroFromQueue()
        // todo: check current turn availability
        val target: HeroHolder =
                if (targetEnemy)
                    roomHolder.battleHolder.enemyHeroes(gameSession.username)[clickedHeroPosition]
                else
                    roomHolder.battleHolder.ownHeroes(gameSession.username)[clickedHeroPosition]
        val spellHolder: SpellHolder = caster.allSpells.find { it.id == spellId } ?: throw IllegalArgumentException("Spell with id $spellId doesn't belong to caster")
        // todo: check spell hero-owner
        val config: SpellConfig = SpellsPool.spellsMap[spellHolder.id]
                ?: throw IllegalArgumentException("Spell with id ${spellHolder.id} doesn't exist!")
        checkPreConditions(caster, target, spellHolder)
        // BUILDING IMPACTS MAP
        val impactsMap = mutableMapOf<HeroHolder, List<IntegerImpactConfig>>()
        impactsMap.put(caster, config.casterIntegerImpacts)
        impactsMap.put(target, config.targetIntegerImpacts)
        config.additionalOwnIntegerImpacts.forEach { position, impacts ->
            run {
                val hero = roomHolder.battleHolder.ownHeroes(gameSession.username)[position]
                impactsMap.put(hero, impacts)
            }
        }
        config.additionalEnemyIntegerImpacts.forEach { position, impacts ->
            run {
                val hero = roomHolder.battleHolder.enemyHeroes(gameSession.username)[position]
                impactsMap.put(hero, impacts)
            }
        }
        // todo: check resists and evasions
        apply(impactsMap)
        spellHolder.onCast()
        checkEndGameHandler.check(roomHolder)
        // todo: run endTurnHandler
        // todo: run startTurnHandler
    }

    private fun checkPreConditions(caster: HeroHolder,
                                   target: HeroHolder,
                                   spellHolder: SpellHolder) {
        preconditionsPool.handlers.forEach { it.handle(caster, target, spellHolder) }
    }

    private fun apply(impactsMap: Map<HeroHolder, List<IntegerImpactConfig>>) {
        impactsMap.forEach { hero, impacts ->
            run {
                impacts.forEach { impact ->
                    when (impact.type) {
                        ImpactType.DAMAGE -> hero.hit(Calculator.calculate(impact))
                        ImpactType.HEAL -> hero.heal(Calculator.calculate(impact))
                        ImpactType.BARRIER -> hero.barrier(Calculator.calculate(impact))
                        ImpactType.MOVE -> hero.move(Calculator.calculate(impact))
                    //                 ImpactType.EFFECT -> hero.effects.add(impact.calculateValue() as EffectHolder)
                    }
                }
            }
        }
    }
}