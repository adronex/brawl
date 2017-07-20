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

    fun castSpell(session: GameSession,
                  spellId: String,
                  victimPosition: Int?,
                  forEnemy: Boolean?)

}