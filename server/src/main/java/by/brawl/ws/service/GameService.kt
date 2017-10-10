package by.brawl.ws.service

import by.brawl.entity.Squad
import by.brawl.ws.holder.gamesession.GameSession

interface GameService {

    fun createTwoPlayersGame(firstSession: GameSession,
                             secondSession: GameSession,
                             firstSquad: Squad,
                             secondSquad: Squad)

    fun setHeroesPositions(session: GameSession,
                           heroesIds: List<String>)

    @Deprecated("Use SpellService from HuiHui package")
    fun castSpell(session: GameSession,
                  spellPosition: Int,
                  targetPosition: Int)

}