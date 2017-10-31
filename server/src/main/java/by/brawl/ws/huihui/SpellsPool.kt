package by.brawl.ws.huihui

import by.brawl.ws.huihui.conf.*
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory

class SpellsPool {

    companion object {
        private val FILE_PATH = "/conf/spells-old.json"
        private val LOG = LoggerFactory.getLogger(SpellsPool::class.java)

        private enum class KEYS() {
            ID,
            CASTER_POSITIONS,
            TARGET_POSITIONS,
            CASTER_IMPACTS,
            TARGET_IMPACTS,
            ADDITIONAL_OWN_IMPACTS,
            ADDITIONAL_ENEMY_IMPACTS,

            TYPE,
            FROM,
            TO,
            VALUE,
            TARGET,
            IMPACTS,

            COOLDOWN,
            SUSPEND,
            CHARGES
        }

        val spellsMap: Map<String, SpellConfig>

        // todo: multiple targets positions (self, enemy)
        init {
            val tempMap = mutableMapOf<String, SpellConfig>()
            val mapper = jacksonObjectMapper()
            val source = this::class.java.getResource(FILE_PATH).readText()
            val spellsTree: JsonNode = mapper.readTree(source) ?: throw IllegalStateException("Spells configs are absent!")
            spellsTree.forEach { spell ->
                val id: String = spell.get(KEYS.ID)?.textValue() ?: throw IllegalStateException("Id of spell is absent, json: ${spell.textValue()}")
                val casterPositions: List<Int>? = spell.get(KEYS.CASTER_POSITIONS)?.map { it.intValue() }
                val targetPositions: List<Int>? = spell.get(KEYS.TARGET_POSITIONS)?.map { it.intValue() }
                val casterImpacts: List<IntegerImpactConfig>? = spell.get(KEYS.CASTER_IMPACTS)?.map { getImpacts(it, id) }
                val targetImpacts: List<IntegerImpactConfig>? = spell.get(KEYS.TARGET_IMPACTS)?.map { getImpacts(it, id) }
                val additionalOwnImpacts = mutableMapOf<Int, List<IntegerImpactConfig>>()

                spell.get(KEYS.ADDITIONAL_OWN_IMPACTS)?.forEach { impact ->
                    val key = impact.get(KEYS.TARGET)?.intValue()
                            ?: throw IllegalStateException("Target of additional impact is absent for spell $id")
                    val value = impact.get(KEYS.IMPACTS)?.map { getImpacts(it, id) }
                            ?: throw IllegalStateException("Impacts object of additional impact are absent for spell $id")
                    additionalOwnImpacts.put(key, value)
                }
                val additionalEnemyImpacts = mutableMapOf<Int, List<IntegerImpactConfig>>()

                spell.get(KEYS.ADDITIONAL_ENEMY_IMPACTS)?.forEach { impact ->
                    val key = impact.get(KEYS.TARGET)?.intValue()
                            ?: throw IllegalStateException("Target of additional impact is absent for spell $id")
                    val value = impact.get(KEYS.IMPACTS)?.map { getImpacts(it, id) }
                            ?: throw IllegalStateException("Impacts object of additional impact are absent for spell $id")
                    additionalOwnImpacts.put(key, value)
                }
                val suspend: Int? = spell.get(KEYS.SUSPEND)?.intValue()
                val cooldown: Int? = spell.get(KEYS.COOLDOWN)?.intValue()
                val charges: Int? = spell.get(KEYS.CHARGES)?.intValue()
                val config = SpellConfig(id,
                                         casterPositions,
                                         targetPositions,
                                         casterImpacts,
                                         targetImpacts,
                                         additionalOwnImpacts,
                                         additionalEnemyImpacts,
                                         null,
                                         suspend,
                                         cooldown,
                                         charges)
                tempMap.put(id, config)
            }
            spellsMap = tempMap
            LOG.debug("Spell configs successfully parsed! Total spells: ${spellsTree.size()}")
        }

        private fun getImpacts(node: JsonNode, spellId: String): IntegerImpactConfig {
            val impactType = ImpactType.valueOf(node.get(KEYS.TYPE)?.textValue() ?: throw IllegalStateException("Type of impact is absent for spell $spellId"))
            return if (node.get(KEYS.VALUE) != null) {
                SingleIntegerImpactConfig(impactType, node.get(KEYS.VALUE)!!.intValue())
            } else {
                val from = node.get(KEYS.FROM)?.intValue() ?: throw IllegalStateException("Type impact is 'RANGED` but `FROM` is absent for spell $spellId")
                val to = node.get(KEYS.TO)?.intValue() ?: throw IllegalStateException("Type impact is 'RANGED` but `TO` is absent for spell $spellId")
                RangeIntegerImpactConfig(impactType, from, to)
            }
        }

        private fun JsonNode.get(key: SpellsPool.Companion.KEYS): JsonNode? {
            return get(key.name)
        }
    }
}
