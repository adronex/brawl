<template>
    <div class="spellsTable">
        <div v-for="spell in spells"
             v-on:click="addSpellId(spell.id)">
            <spell :key="spell.id"
                   :spellId="spell.id"
                   :style="getOpacityForImage(spell.id)">
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
        props: {
            oldHeroSpellsIds: {
                type: Array,
                required: true
            }
        },
        mounted: function () {
            StaticData.getSpellsPromise().then(it => this.spells = it);
            this.chosenSpellsIds = this.oldHeroSpellsIds;
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
            },
            getOpacityForImage(spellId) {
                let opacityValue = (this.chosenSpellsIds.indexOf(spellId)) !== -1 ? 1 : 0.5;
                return {'opacity': opacityValue};
            }
        }
    }

</script>

<style>
    .spellsTable {
        float: left;
        min-width: 70px;
    }
</style>