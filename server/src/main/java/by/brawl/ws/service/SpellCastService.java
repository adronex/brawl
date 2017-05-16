package by.brawl.ws.service;

import by.brawl.ws.holder.BattlefieldHolder;

public interface SpellCastService {

    BattlefieldHolder castSpell(String id,
                                String senderAccountId,
                                String senderId,
                                Integer target,
                                Boolean enemy,
                                BattlefieldHolder battlefieldHolder);
}
