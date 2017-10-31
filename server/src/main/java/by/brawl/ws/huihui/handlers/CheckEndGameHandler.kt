package by.brawl.ws.huihui.handlers

import by.brawl.ws.holder.RoomHolder
import org.springframework.stereotype.Component

@Component
class CheckEndGameHandler: HuiHandler {

    fun check(roomHolder: RoomHolder): Boolean {
        return roomHolder.isGameFinished()
    }
}