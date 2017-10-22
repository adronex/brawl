<template>
    <div class="spellTable">
        <div v-for="spell in spells"
             v-on:click="addSpellId(spell.id)">
            <spell :key="spell.id"
                   :spellId="spell.id">
            </spell>
        </div>
    </div>
</template>

<script>
    'use strict';
    import StaticData from '../../service/staticData'
    import Spell from '../../components/menu/Spell.vue'

    export default {
        components: {
            Spell
        },
        data: function () {
            return {
                spells: [],
                chosenSpellsIds: []
            }
        },
        mounted: function () {
            StaticData.getSpellsPromise().then(it => this.spells = it);
        },
        methods: {
            addSpellId(id) {
                if (this.chosenSpellsIds.includes(id)) {
                    this.chosenSpellsIds = this.chosenSpellsIds.filter(item => item !== id);
                } else {
                    this.chosenSpellsIds.push(id);
                }
                if (this.chosenSpellsIds.length > 4) {
                    this.chosenSpellsIds = this.chosenSpellsIds.slice(1);
                }
                console.log(this.chosenSpellsIds);
            }
        }
    }

</script>

<style>
    .spellTable {
        float: left;
        min-width: 70px;
    }
</style>