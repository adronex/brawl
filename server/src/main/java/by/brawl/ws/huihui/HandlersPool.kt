package by.brawl.ws.huihui

import by.brawl.ws.huihui.handlers.preconditions.*
import org.springframework.stereotype.Component

@Component
class HandlersPool {
    val checkCasterAvailabilityHandler = CheckCasterAvailabilityHandler()
    val checkCasterPositionHandler = CheckCasterPositionHandler()
    val checkSpellHasChargesHandler = CheckSpellHasChargesHandler()
    val checkSpellOnCooldownHandler = CheckSpellOnCooldownHandler()
    val checkSpellOnSuspendHandler = CheckSpellOnSuspendHandler()
    val checkTargetAvailabilityHandler = CheckTargetAvailabilityHandler()
    val checkTargetPositionHandler = CheckTargetPositionHandler()
}