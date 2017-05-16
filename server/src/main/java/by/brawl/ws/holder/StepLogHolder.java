package by.brawl.ws.holder;

import java.util.HashMap;
import java.util.Map;

public class StepLogHolder {
    private String accountId;
    private String spellId;
    private String senderId;
    private Map<String, Integer> damageByReceivers = new HashMap<>();

    public StepLogHolder(String accountId, String spellId, String senderId) {
        this.accountId = accountId;
        this.spellId = spellId;
        this.senderId = senderId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getSpellId() {
        return spellId;
    }

    public String getSenderId() {
        return senderId;
    }

    public Map<String, Integer> getDamageByReceivers() {
        return damageByReceivers;
    }
}