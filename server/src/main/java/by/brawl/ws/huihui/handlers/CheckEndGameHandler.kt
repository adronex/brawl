package by.brawl.ws.huihui.handlers

import by.brawl.ws.holder.BattlefieldHolder
import org.springframework.stereotype.Component

@Component
class CheckEndGameHandler: HuiHandler {

    fun check(battlefieldHolder: BattlefieldHolder): Boolean {
        return battlefieldHolder.isGameFinished()
    }
}