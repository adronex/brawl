<template>
    <div class="page">

        <div class="backToMenu">
            <div>
                Login as {{ login }}
            </div>
            <v-link href="/">Menu</v-link>
        </div>

        <div class="queueWrapper">
            <div class="queue" v-show="queue.length > 0">
                <img src="images/1.jpg">
                <img src="images/2.JPG">
                <img src="images/3.jpg">
                <img src="images/4.jpeg">
                <img src="images/5.jpg">
                <img src="images/6.jpg">
                <img src="images/7.jpg">
                <img src="images/8.jpg">
            </div>
        </div>


        <div class="team">
            <div class="myTeam">
                <div class="hero my" v-for="battleHero in myHeroes" v-on:click="chosenHero = battleHero">
                    {{ battleHero.id }}
                </div>
            </div>
        </div>
        <div class="team">
            <div class="enemyTeam">
                <div class="hero enemy" v-for="battleHero in enemyHeroes" v-on:click="chosenHero = battleHero"></div>
            </div>
        </div>

        <hero-block class="currentHeroBar"
                    :hero="currentHero"></hero-block>
        <hero-block class="chosenHeroBar"
                    :hero="chosenHero"
                    v-show="chosenHero.id"></hero-block>


        <div class="battleLogBlock">
            <p v-for="message in battleLogMessages">{{message}}</p>
        </div>
    </div>
</template>

<script>
    'use strict';
    import Routes from '../service/routes'
    import Auth from '../service/auth'
    import Ws from '../service/ws'

    import VLink from '../components/Link.vue'
    import Spell from '../components/battlefield/Spell.vue'
    import HeroBlock from '../components/battlefield/HeroBlock.vue'

    export default {
        components: {
            VLink,
            Spell,
            HeroBlock
        },
        data: function () {
            return {
                login: Auth.getLogin(),
                battleLogMessages: [],
                queue: [],
                myHeroes: [],
                enemyHeroes: [],
                currentHero: {},
                chosenHero: {}
            }
        },
        props: {
//            squadId: {
//                type: Object,
//                required: true
//            }
        },
        mounted: function () {
            if (!Ws.isConnected()) {
                Routes.go(this, '/play');
                console.log('Websocket connection lost. Redirecting to play screen.');
            }
            Ws.subscribeOnMessageEvent(this);
        },
        methods: {
            handleNotification(notification) {
                // IGNORED PROPERTIES because Vuejs doesn't support 'key = undefined' or 'delete key'
                let ignoredProperties = [];
                if (notification.myHeroes) {
                    this.myHeroes = notification.myHeroes;
                    ignoredProperties.push('myHeroes');
                }
                if (notification.enemyHeroes) {
                    this.enemyHeroes = notification.enemyHeroes;
                    ignoredProperties.push('enemyHeroes');
                }
                Object.keys(notification).forEach(key => {
                    if (ignoredProperties.indexOf(key) === -1) {
                        const message = {
                            [key]: notification[key]
                        };
                        this.battleLogMessages.push(message);
                    }
                });
            }
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

    .backToMenu {
        display: block;
        float: left;
        background-color: #d3ffb5;
    }

    .queueWrapper {
        display: block;
        width: 640px;
        height: 60px;
        margin: auto;
        text-align: center;
        vertical-align: middle;
        padding-top: 10px;
    }

    .queueWrapper .queue {
        background-color: #ecf7f7;
    }

    .queueWrapper .queue img {
        min-width: 50px;
        min-height: 50px;
        background-color: darkgrey;
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

    .battleLogBlock {
        float: left;
        width: 50%;
        max-height: 300px;
        background-color: #ded9ff;
        overflow-y: auto;
    }

    .currentHeroBar {
        background-color: #ffcfec;
    }

    .chosenHeroBar {
        background-color: #aeb1f2;
    }
</style>