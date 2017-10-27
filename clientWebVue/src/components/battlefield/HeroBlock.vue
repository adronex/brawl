<template>
    <div class="heroBlock">
        <div class="heroInfo">
            <div class="heroAvatar"
                 :style="cssBackgroundImage"></div>
            <div class="heroDescription">
                <p>{{hero.name}}</p>
                <p>MNOGA-MNOGA BYKAFFF</p>
                <p>MNOGA-MNOGA BYKAFFF</p>
                <p>MNOGA-MNOGA BYKAFFF</p>
            </div>
        </div>
        <div class="spellBlock">
            <div class="spellBar">
                <div class="spell"
                     v-for="spell in hero.spells"
                     v-on:click="onSpellClick(spell)">
                    <spell :key="spell.id"
                           :spellId="spell.id"></spell>
                </div>
            </div>
            <div class="spellDescription">
                <p>{{chosenSpell.id}}</p>
                <p>MNOGA-MNOGA BYKAFFF</p>
                <p>MNOGA-MNOGA BYKAFFF</p>
                <p>MNOGA-MNOGA BYKAFFF</p>
                <p>MNOGA-MNOGA BYKAFFF</p>
                <p>MNOGA-MNOGA BYKAFFF</p>
                <p>MNOGA-MNOGA BYKAFFF</p>
                <p>MNOGA-MNOGA BYKAFFF</p>
                <p>MNOGA-MNOGA BYKAFFF</p>
                <p>MNOGA-MNOGA BYKAFF</p>
            </div>
        </div>
    </div>
</template>

<script>
    'use strict';
    import StaticData from '../../service/staticData.js'
    import Spell from '../battlefield/Spell.vue'

    export default {
        components: {
            Spell
        },
        data: function () {
            return {
                cssBackgroundImage: {},
                chosenSpell: {}
            }
        },
        props: {
            hero: Object,
            required: true
        },
        mounted: function () {

            let backgroundProperty1 = require(`vectors/bodyparts/head-3.svg`);
            let backgroundProperty2 = require(`vectors/bodyparts/head-2.svg`);
//            this.currentHeroAvatarCss = {'background': `url(${backgroundProperty1})`, 'background-size': '100% 100%', 'transform': 'scaleX(-1)'};
//            this.targetHeroAvatarCss = {'background': `url(${backgroundProperty2})`, 'background-size': '100% 100%'};
        },
        methods: {
            onSpellClick(spell) {
                StaticData.getSpellsPromise().then(it => {
                    this.chosenSpell = it[spell.id];
                    if (!this.spell) {
                        this.spell = {};
                        console.warn(`Spell static data for id ${this.spellId} is absent.}`)
                    }
                });
            }
        }
    }
</script>

<style>

    .heroBlock {
        float: left;
        width: 50%;
        max-height: 300px;
        overflow-y: auto;
    }

    .heroBlock .heroInfo {
        max-width: 300px;
        float: left;
    }

    .heroBlock .spellBlock {
        float: left;
    }

    .heroBlock .spellBlock .spellBar {
        margin-top: 15px;
    }

    .heroBlock .spellBlock .spellBar .spell {
        width: 50px;
        height: 50px;
    }

    .heroBlock .heroAvatar {
        width: 200px;
        height: 200px;
        float: left;
        margin: 40px 40px 10px;
    }

    .heroBlock .heroDescription {
        width: 200px;
        float: left;
        margin-left: 40px;
    }

    .heroBlock .spellBlock .spellDescription {
        width: 200px;
        margin-left: 40px;
    }
</style>