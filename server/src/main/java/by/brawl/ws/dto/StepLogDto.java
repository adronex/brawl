package by.brawl.ws.dto;

import by.brawl.ws.holder.StepLogHolder;

import java.util.Objects;

public class StepLogDto {

    private String spellId;
    private String senderId;
    private Boolean enemy;

    public StepLogDto(StepLogHolder stepLogHolder, String receiverId) {
        spellId = stepLogHolder.getSpellId();
        senderId = stepLogHolder.getCasterId();
        this.enemy = !Objects.equals(receiverId, stepLogHolder.getSenderId());
    }

    public String getSpellId() {
        return spellId;
    }

    public String getSenderId() {
        return senderId;
    }

    public Boolean getEnemy() {
        return enemy;
    }
}
