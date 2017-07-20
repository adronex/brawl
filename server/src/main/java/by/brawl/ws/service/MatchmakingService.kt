package by.brawl.ws.service

import by.brawl.ws.holder.gamesession.GameSession

interface MatchmakingService {

    fun addInPool(session: GameSession, squadId: String)

}