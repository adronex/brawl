package by.brawl.ws.holder;

import by.brawl.entity.Spell;

public class SpellHolder {
	private String id;
	private Integer cooldownTurns = 0;
	private Integer suspendTurns = 0;

	public SpellHolder(Spell spell) {
		id = spell.getId();
	}

	public Boolean isAvailable() {
		return cooldownTurns == 0 && suspendTurns == 0;
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
