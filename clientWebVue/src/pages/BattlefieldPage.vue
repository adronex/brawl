<template>
    <div class="page">
        <div class="gallery">
            <div class="menu">
                <div>
                    Login as {{ login }}
                </div>
                <v-link href="/">Menu</v-link>
            </div>
            <div class="images">
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
        </div>
        <div class="footer">
            <div class="heroBar">
                <div class="main_image"><img src="images/2.jpg"></div>
                <div class="spellBar">
                    <spell :spellId="'ELDRICH_PULL'"></spell>
                    <spell :spellId="'DIVINE_COMFORT'"></spell>
                    <spell :spellId="'UPPERCUT'"></spell>
                    <spell :spellId="'SELF_HEAL'"></spell>
                </div>
                <div class="heroDescription">
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
                <div class="spellDescription">
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                    <p>MNOGA-MNOGA BYKAFFF</p>
                </div>
            </div>
        </div>

        <div class="heroBar chosenHeroBar">
            <div class="main_image"><img src="images/2.JPG"></div>
            <div class="spellBar">
                <spell :spellId="'SUCKER_PUNCH'"></spell>
                <spell :spellId="'SHOOT_THEM_ALL'"></spell>
                <spell :spellId="'SUCKER_PUNCH'"></spell>
                <spell :spellId="'SUCKER_PUNCH'"></spell>
            </div>
            <div class="heroDescription">
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
            <div class="spellDescription">
                <p>MNOGA-MNOGA BYKAFFF</p>
                <p>MNOGA-MNOGA BYKAFFF</p>
                <p>MNOGA-MNOGA BYKAFFF</p>
                <p>MNOGA-MNOGA BYKAFFF</p>
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
        },
        methods: {}
    }
</script>
<style>
    .page {
        width: 100%;
        height: 100%;
        font-family: "PT Sans", sans-serif;
        font-size: 14px;
    }

    .gallery {
        width: 100%;
        height: 50%;
        background-color: #ecf7f7;
    }

    .menu {
        display: block;
        float: left;
        background-color: #d3ffb5;
    }

    .images {
        display: block;
        width: 640px;
        height: 80px;
        margin: auto;
    }

    .images img {
        min-width: 50px;
        min-height: 50px;
    }

    .spellBar {
        margin-top: 15px;
    }

    .spellBar .spell {
        width: 50px;
        height: 50px;
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

    .hero {
        display: inline-block;
        width: 70px;
        height: 140px;
        margin-right: 10px;
        margin-left: 10px;
        background-color: brown;
    }

    .hero.my {
        float: right;
    }

    .hero.enemy {
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

    .main_image img {
        width: 200px;
        height: 200px;
        float: left;
        margin: 40px;
    }

    .heroDescription {
        width: 200px;
        float: left;
        margin-left: 40px;
    }

    .spellDescription {
        width: 200px;
        margin-left: 40px;
    }
</style>