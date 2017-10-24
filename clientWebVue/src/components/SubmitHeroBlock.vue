<template>
    <div class="editHeroBlock">
        <div>
            <spells-table ref="spellsTable"
                          :oldHeroSpellsIds="hero.spells"></spells-table>
            <input class="heroNameInput"
                   type="text"
                   placeholder="Hero name"
                   v-model="hero.name"/>
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
    import Rest from '../service/rest'
    import Urls from '../service/urls'
    import Hero from '../components/menu/Hero.vue'
    import SpellsTable from './menu/SpellsTable.vue'

    export default {
        components: {
            Hero,
            SpellsTable
        },
        data: function () {
            return {
                hero: {},
                errorMessages: []
            }
        },
        props: {
            oldHero: {
                type: Object,
                required: true
            }
        },
        beforeMount: function () {
            let newHero = JSON.parse(JSON.stringify(this.oldHero));
            newHero.spells = newHero.spells.map(it => it.id);
            this.hero = newHero;
        },
        methods: {
            submitHero() {
                this.errorMessages = [];
                let spellsBeenChosen = this.$refs.spellsTable.chosenSpellsIds.length;
                let spellsCountCorrect = spellsBeenChosen === 4;
                let heroNameCorrect = !!this.hero.name;
                if (spellsCountCorrect && heroNameCorrect) {
                    this.hero.spells = this.$refs.spellsTable.chosenSpellsIds;
                    this.hero.bodyparts = [];
                    Rest.promisedAuthenticatedRequest(Urls.api.heroes.submit, {
                        method: 'POST',
                        body: JSON.stringify(this.hero)
                    });
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