package by.brawl.ws.huihui.handlers

import by.brawl.ws.holder.BattlefieldHolder

class CheckEndGameHandler {

    fun check(battlefieldHolder: BattlefieldHolder): Boolean {
        return battlefieldHolder.isGameFinished()
    }
}