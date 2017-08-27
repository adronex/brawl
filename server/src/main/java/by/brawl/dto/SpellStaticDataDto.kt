package by.brawl.dto

data class SpellStaticDataDto(val id: String,
                              val name: String,
                              val targetable: Boolean,
                              val myTargets: Set<Int>,
                              val enemyTargets: Set<Int>)