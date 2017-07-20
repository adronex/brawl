package by.brawl.ws.dto

import by.brawl.ws.holder.GameState
import by.brawl.ws.holder.HeroHolder
import java.util.*

class MulliganDto(mulliganHeroes: Map<String, List<HeroHolder>>,
                  receiverName: String,
                  val gameState: GameState) : AbstractDto(), JsonDto {

    var myHeroes: List<HeroDto> = ArrayList()
        private set
    var enemyHeroes: List<HeroDto> = ArrayList()
        private set
    val heroSpellsIds = HashMap<String, List<String>>()

   // val heroSpellsIds = mulliganHeroes[receiverName]!!.associateBy ({it.id}, {it.allSpells.map { it.id }})

    // todo: refactor
    init {

        mulliganHeroes.forEach { key, value ->
            if (key == receiverName) {
                myHeroes = value.map { HeroDto(it) }
            } else {
                enemyHeroes = value.map { HeroDto(it) }
            }
        }

        mulliganHeroes.forEach { key, _ ->
            if (key == receiverName) {
                for (heroHolder in mulliganHeroes[receiverName]!!) {
                    heroSpellsIds.put(heroHolder.id, heroHolder.allSpells.map { it.id })
                }
            }
        }
    }

}