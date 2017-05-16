package by.brawl.ws.dto;

import by.brawl.ws.holder.SpellHolder;

public class SpellDto {
    private String id;
    private Integer cooldownTurns;
    private Integer suspendTurns;

    public SpellDto(SpellHolder spellHolder) {
        id = spellHolder.getId();
        cooldownTurns = spellHolder.getCooldownTurns();
        suspendTurns = spellHolder.getSuspendTurns();
    }

    public String getId() {
        return id;
    }

    public Integer getCooldownTurns() {
        return cooldownTurns;
    }

    public Integer getSuspendTurns() {
        return suspendTurns;
    }
}
