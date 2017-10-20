<template>
    <div class="spellTable">
        <spell v-for="spell in spells"
               :key="spell.id"
               :spellId="spell.id">
        </spell>
        <input type="button" v-on:click="hui()" value="dada"/>
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
            this.spells = StaticData.getSpells();
        },
        methods: {
            hui: function () {
                this.spells = StaticData.getSpells();
            }
        }
    }

</script>

<style>
    .spellTable {
        float: left;
        width: 100px;
        height: 100px;
        margin-left: 50px;
        background-color: #c6c4c4;
    }
</style>