package by.brawl.ws.holder;

import java.util.HashMap;
import java.util.Map;

public class StepLogHolder {
    private String senderId;
    private String spellId;
    private String casterId;
    private Map<String, Integer> damageByReceivers = new HashMap<>();

    public StepLogHolder(String senderId, String spellId, String casterId) {
        this.senderId = senderId;
        this.spellId = spellId;
        this.casterId = casterId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getSpellId() {
        return spellId;
    }

    public String getCasterId() {
        return casterId;
    }

    public Map<String, Integer> getDamageByReceivers() {
        return damageByReceivers;
    }
}