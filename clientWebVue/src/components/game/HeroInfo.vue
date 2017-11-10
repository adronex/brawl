<template>
    <div class="heroInfo">
        <div class="heroInfo">
            <div class="heroAvatar"
                 :style="cssHeroAvatar"></div>
            <div class="heroDescription">
                <p>{{hero.name}}</p>
                <p>{{hero.id}}</p>
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
                <div v-if="chosenSpell.id">
                    <p>{{chosenSpell.id}}</p>
                    <p>{{chosenSpell.config.casterPositions}}</p>
                    <p>{{chosenSpell.config.targetOwnPositions}} {{chosenSpell.config.targetEnemyPositions}}</p>
                </div>
                <div v-else>
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
    </div>
</template>

<script>
    'use strict';
    import StaticData from '../../service/staticData'
    import Spell from './Spell.vue'

    export default {
        components: {
            Spell
        },
        data: function () {
            return {
                cssHeroAvatar: {},
                chosenSpell: {}
            }
        },
        props: {
            hero: Object,
            required: true
        },
        watch: {
            hero: function (val) {
                if (this.hero.id) {
                    let head = this.hero.bodyparts.find(it => it.type === 'HEAD');
                    this.cssHeroAvatar = StaticData.getBodypartCss(head.id);
                }
            }
        },
        mounted: function () {
    //        this.chosenSpell = this.hero.spells[0];
            // todo: apply transform to one avatar
// this.targetHeroAvatarCss = {'transform': 'scaleX(-1)'};
        },
        methods: {
            onSpellClick(spell) {
                StaticData.getSpellsPromise().then(it => {
                    this.chosenSpell = spell;
                    this.chosenSpell.config = it[spell.id];
                    if (!this.chosenSpell.config) {
                        this.chosenSpell.config = {};
                        console.warn(`Spell static data for id ${spell.id} is absent.}`)
                    }
                });
            }
        }
    }
</script>

<style>

    .heroInfo {
        float: left;
        width: 50%;
        max-height: 300px;
        overflow-y: auto;
    }

    .heroInfo .heroInfo {
        max-width: 300px;
        float: left;
    }

    .heroInfo .spellBlock {
        float: left;
    }

    .heroInfo .spellBlock .spellBar {
        margin-top: 15px;
    }

    .heroInfo .spellBlock .spellBar .spell {
        width: 50px;
        height: 50px;
    }

    .heroInfo .heroAvatar {
        width: 200px;
        height: 200px;
        float: left;
        margin: 40px 40px 10px;
        background-size: 100% 100%;
    }

    .heroInfo .heroDescription {
        width: 200px;
        float: left;
        margin-left: 40px;
    }

    .heroInfo .spellBlock .spellDescription {
        width: 200px;
        margin-left: 40px;
    }
</style>