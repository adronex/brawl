<template>
    <div class="page">

        <div class="menu">
            <div>
                Login as {{ login }}
            </div>
            <v-link href="/">Menu</v-link>
        </div>

        <div class="queue">
            <img src="images/1.jpg">
            <img src="images/2.JPG">
            <img src="images/3.jpg">
            <img src="images/4.jpeg">
            <img src="images/5.jpg">
            <img src="images/6.jpg">
            <img src="images/7.jpg">
            <img src="images/8.jpg">
        </div>


        <div class="team">
            <div class="myTeam">
                <div class="hero my"></div>
                <div class="hero my"></div>
                <div class="hero my"></div>
                <div class="hero my"></div>
                <div class="hero my"></div>
                <div class="hero my"></div>
            </div>
        </div>
        <div class="team">
            <div class="enemyTeam">
                <div class="hero enemy"></div>
                <div class="hero enemy"></div>
                <div class="hero enemy"></div>
                <div class="hero enemy"></div>
                <div class="hero enemy"></div>
                <div class="hero enemy"></div>
            </div>
        </div>

        <div class="heroBar">
            <div class="heroBlock">
                <div class="heroAvatar"
                     :style="currentHeroAvatarCss"></div>
                <div class="heroDescription">
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                </div>
            </div>
            <div class="spellBlock">
                <div class="spellBar">
                    <spell :spellId="'ELDRICH_PULL'"></spell>
                    <spell :spellId="'DIVINE_COMFORT'"></spell>
                    <spell :spellId="'UPPERCUT'"></spell>
                    <spell :spellId="'SELF_HEAL'"></spell>
                </div>
                <div class="spellDescription">
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                </div>
            </div>
        </div>

        <div class="heroBar chosenHeroBar">
            <div class="heroBlock">
                <div class="heroAvatar"
                     :style="targetHeroAvatarCss"></div>
                <div class="heroDescription">
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                </div>
            </div>
            <div class="spellBlock">
                <div class="spellBar">
                    <spell :spellId="'SUCKER_PUNCH'"></spell>
                    <spell :spellId="'SHOOT_THEM_ALL'"></spell>
                    <spell :spellId="'SUCKER_PUNCH'"></spell>
                    <spell :spellId="'SUCKER_PUNCH'"></spell>
                </div>
                <div class="spellDescription">
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    'use strict';
    import Auth from '../service/auth'
    import VLink from '../components/Link.vue'
    import Spell from '../components/battlefield/Spell.vue'

    export default {
        components: {
            VLink,
            Spell
//            Hero,
//            SpellsTable
        },
        data: function () {
            return {
                currentHeroAvatarCss: {},
                targetHeroAvatarCss: {},
                login: Auth.getLogin(),
                hero: {},
                errorMessages: []
            }
        },
        props: {
//            squadId: {
//                type: Object,
//                required: true
//            }
        },
        beforeMount: function () {
            let backgroundProperty1 = require(`vectors/bodyparts/head-3.svg`);
            let backgroundProperty2 = require(`vectors/bodyparts/head-2.svg`);
            this.currentHeroAvatarCss = {'background': `url(${backgroundProperty1})`, 'background-size': '100% 100%', 'transform': 'scaleX(-1)'};
            this.targetHeroAvatarCss = {'background': `url(${backgroundProperty2})`, 'background-size': '100% 100%'};
        },
        methods: {
//            getHeroAvatarCss() {
//                let backgroundProperty = require(`vectors/bodyparts/head-3.svg`);
//                return {'background-image': `url(${backgroundProperty})`};
//            }
        }
    }
</script>
<style>
    .page {
        width: 100%;
        height: 100%;
        font-family: "PT Sans", sans-serif;
        font-size: 14px;
    }

    .menu {
        display: block;
        float: left;
        background-color: #d3ffb5;
    }

    .queue {
        background-color: #ecf7f7;
        display: block;
        width: 640px;
        height: 80px;
        margin: auto;
    }

    .queue img {
        min-width: 50px;
        min-height: 50px;
    }

    .team {
        display: inline-block;
        float: left;
        width: 50%;
        height: 150px;
        margin-top: 20px;
        margin-bottom: 50px;
    }

    .team .myTeam {
        padding-right: 20px;
    }

    .team .enemyTeam {
        padding-left: 20px;
    }

    .team .hero {
        display: inline-block;
        width: 70px;
        height: 140px;
        margin-right: 10px;
        margin-left: 10px;
        background-color: brown;
    }

    .team .hero.my {
        float: right;
    }

    .team .hero.enemy {
        float: left;
    }

    .heroBar {
        float: left;
        width: 50%;
        max-height: 300px;
        background-color: #ffcfec;
    }

    .heroBar.chosenHeroBar {
        background-color: #aeb1f2;
    }

    .heroBar .heroBlock {
        max-width: 300px;
        float: left;
    }

    .heroBar .spellBlock {
        float: left;
    }

    .heroBar .spellBlock .spellBar {
        margin-top: 15px;
    }

    .heroBar .spellBlock .spellBar .spell {
        width: 50px;
        height: 50px;
    }

    .heroBar .heroAvatar {
        width: 200px;
        height: 200px;
        float: left;
        margin: 40px;
    }

    .heroBar .heroDescription {
        width: 200px;
        float: left;
        margin-left: 40px;
    }

    .heroBar .spellBlock .spellDescription {
        width: 200px;
        margin-left: 40px;
    }
</style>