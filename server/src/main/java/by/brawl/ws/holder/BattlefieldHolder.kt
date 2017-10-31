package by.brawl.ws.holder

import by.brawl.entity.Squad

class BattlefieldHolder() {
    //todo: when kotlin 1.2 will out - make it work with lateinit and isInitialized (remove hueviches)
    private var firstSquadHuevich: Boolean = false
    private var secondSquadHuevich: Boolean = false
    private lateinit var firstSquad: SquadHolder
    private lateinit var secondSquad: SquadHolder

    private val heroes:List<SquadHolder> by lazy {
        return@lazy listOf<SquadHolder>(firstSquad, secondSquad)
    }

    fun addSquad(squadHolder: SquadHolder) {
        // todo: lateinit (see line 6)
        if (!firstSquadHuevich) {
            firstSquad = squadHolder
            firstSquadHuevich = true
        } else if (!secondSquadHuevich) {
            secondSquad = squadHolder
            secondSquadHuevich = true
        } else {
            throw IllegalStateException("Squads have already been set")
        }
    }

    fun isReady():Boolean = firstSquadHuevich && secondSquadHuevich

    fun ownHeroes(receiverName: String): List<HeroHolder> {
        return when (receiverName) {
            heroes[0].ownerName -> heroes[0].heroes
            heroes[1].ownerName -> heroes[1].heroes
            else -> throw IllegalStateException("Squad for account $receiverName is absent")
        }
    }

    fun enemyHeroes(receiverName: String): List<HeroHolder> {
        return when (receiverName) {
            heroes[0].ownerName -> heroes[1].heroes
            heroes[1].ownerName -> heroes[0].heroes
            else -> throw IllegalStateException("Squad for account $receiverName is absent")
        }
    }

    fun allHeroes(): List<HeroHolder> = heroes[0].heroes + heroes[1].heroes
}