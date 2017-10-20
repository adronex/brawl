<template>
    <div class="spellTable">
        <spell v-for="spell in spells"
               :key="spell.id"
               :spellId="spell.id">
        </spell>
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
                spells: []
            }
        },
        mounted: function () {
            StaticData.getSpellsPromise().then(it => this.spells = it);
        }
    }

</script>

<style>
    .spellTable {
        float: left;
        max-width: 70px;
    }
</style>