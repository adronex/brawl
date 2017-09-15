package by.brawl.ws.service

import by.brawl.ws.holder.BattlefieldHolder

@Deprecated("use huihui package")
interface SpellCastService {

    fun castSpell(spellId: String,
                  senderId: String,
                  casterId: String,
                  victimPosition: Int?,
                  forEnemy: Boolean?,
                  battlefieldHolder: BattlefieldHolder): BattlefieldHolder

}