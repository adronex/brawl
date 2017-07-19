package by.brawl.ws.service

import by.brawl.ws.holder.BattlefieldHolder

interface SpellCastService {

    fun castSpell(id: String,
                  senderAccountId: String,
                  senderId: String,
                  target: Int?,
                  enemy: Boolean?,
                  battlefieldHolder: BattlefieldHolder): BattlefieldHolder
}
