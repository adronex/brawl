package by.brawl.ws.dto

import by.brawl.ws.holder.GameState
import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SquadHolder
import java.util.*

class MulliganDto(val ownHeroes: List<HeroDto>,
                  val enemyHeroes: List<HeroDto>,
                  val gameState: GameState) : AbstractDto(), JsonDto {

    init {
        this.enemyHeroes.forEach {
            it.spells.clear()
        }
    }

}