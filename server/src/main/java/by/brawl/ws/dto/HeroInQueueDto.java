package by.brawl.ws.dto;

public class HeroInQueueDto {
	private String id;
	private Boolean enemy;

	public HeroInQueueDto(String id, Boolean enemy) {
		this.id = id;
		this.enemy = enemy;
	}

	public String getId() {
		return id;
	}

	public Boolean getEnemy() {
		return enemy;
	}
}
