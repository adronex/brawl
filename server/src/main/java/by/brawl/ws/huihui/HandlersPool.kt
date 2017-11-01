package by.brawl.ws.huihui

import by.brawl.ws.huihui.handlers.preconditions.*
import org.springframework.stereotype.Component

@Component
class HandlersPool {
    val handlers = listOf(
            CheckCasterAvailabilityHandler(),
            CheckCasterPositionHandler(),
            CheckSpellHasChargesHandler(),
            CheckSpellOnCooldownHandler(),
            CheckSpellOnSuspendHandler(),
            CheckTargetAvailabilityHandler(),
            CheckTargetPositionHandler())
}