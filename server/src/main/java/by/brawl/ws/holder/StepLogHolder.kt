package by.brawl.ws.holder

import java.util.HashMap

class StepLogHolder(val senderId: String, val spellId: String, val casterId: String) {
    val damageByReceivers = HashMap<String, Int>()
}