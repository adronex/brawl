<template>
    <div class="editHeroBlock">
        <div>
            <spells-table ref="spellsTable"></spells-table>
            <input class="heroNameInput"
                   type="text"
                   placeholder="Hero name"
                   v-model="heroName"/>
            <input type="button"
                   value="Submit"
                   v-on:click="submitHero()"/>
        </div>
        <div class="errors">
            <p v-for="msg in errorMessages">{{msg}}</p>
        </div>
    </div>
</template>

<script>
    'use strict';
    import Hero from '../components/menu/Hero.vue'
    import SpellsTable from './menu/SpellsTable.vue'

    export default {
        components: {
            Hero,
            SpellsTable
        },
        data: function () {
            return {
                hero: [],
                heroName: ``,
                errorMessages: []
            }
        },
        mounted: function () {
        },
        methods: {
            submitHero() {
                this.errorMessages = [];
                let spellsBeenChosen = this.$refs.spellsTable.chosenSpellsIds.length;
                let spellsCountCorrect = spellsBeenChosen === 4;
                let heroNameCorrect = !!this.heroName;
                if (spellsCountCorrect && heroNameCorrect) {
                    console.log('yep');
                    return;
                }
                if (!spellsCountCorrect) this.errorMessages.push(`Spells count: ${spellsBeenChosen}, required: 4`);
                if (!heroNameCorrect) this.errorMessages.push(`Hero name is empty`);
            }
        }
    }
</script>
<style>
    .editHeroBlock {
        float: left;
        margin-left: 10px;
        margin-top: 30px;
    }

    .heroNameInput {
        width: 100px;
    }
    .errors {
        float: left;
        color: red;
    }
</style>