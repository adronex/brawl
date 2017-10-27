<template>
    <div class="page">

        <div class="backToMenu">
            <div>
                Login as {{ login }}
            </div>
            <v-link href="/play">Menu</v-link>
        </div>

        <div class="queueWrapper">
            <div class="queue" v-show="queue.length > 0">
                <div class="heroInQueue"
                     v-for="heroInQueue in queue">
                    {{ heroInQueue.id }}
                </div>
            </div>
        </div>


        <div class="team">
            <div class="myTeam">
                <div class="hero my"
                     v-for="battleHero in myHeroes"
                     v-on:click="onHeroClick(battleHero)"
                     v-on:mouseover="onHeroHover(battleHero)"
                     :style="getHeroStyle(battleHero)">
                    {{ battleHero.id }}
                </div>
            </div>
        </div>
        <div class="team">
            <div class="enemyTeam">
                <div class="hero enemy"
                     v-for="battleHero in enemyHeroes"
                     v-on:click="onHeroClick(battleHero)"
                     v-on:mouseover="onHeroHover(battleHero)"
                     :style="getHeroStyle(battleHero)"></div>
            </div>
        </div>

        <div class="startButton">
            <input type="button"
                   value="Start"
                   v-show="mulliganHeroesIds.length === 4"
                   v-on:click="startGame()"/>
        </div>

        <hero-block class="currentHeroBlock"
                    :hero="currentHero"
                    ref="currentHeroBlock">
        </hero-block>
        <hero-block class="chosenHeroBlock"
                    :hero="chosenHero"
                    v-show="chosenHero.id">
        </hero-block>


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
                chosenHero: {},
                gameState: {},
                mulliganHeroesIds: []
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
                if (notification.gameState) {
                    this.gameState = notification.gameState;
                    ignoredProperties.push('gameState');
                }
                if (notification.queue) {
                    this.queue = notification.queue;
                    ignoredProperties.push('queue');
                    if (this.queue.length > 0) {
                        let currentHero = this.myHeroes.find(it => it.id === this.queue[0].id);
                        if (!currentHero) {
                            currentHero = this.enemyHeroes.find(it => it.id === this.queue[0].id);
                        }
                        this.currentHero = currentHero;
                    }
                }
                Object.keys(notification).forEach(key => {
                    if (ignoredProperties.indexOf(key) === -1) {
                        const message = {
                            [key]: notification[key]
                        };
                        this.battleLogMessages.push(message);
                    }
                });
            },
            onHeroHover(hero) {
                this.chosenHero = hero;
            },
            onHeroClick(hero) {
                if (this.gameState === 'MULLIGAN') {
                    if (this.mulliganHeroesIds.includes(hero.id)) {
                        this.mulliganHeroesIds = this.mulliganHeroesIds.filter(item => item !== hero.id);
                    } else {
                        this.mulliganHeroesIds.push(hero.id);
                    }
                    if (this.mulliganHeroesIds.length > 4) {
                        this.mulliganHeroesIds = this.mulliganHeroesIds.slice(1);
                    }
                }
                if (this.gameState === 'PLAYING') {
                    const chosenSpellId = this.$refs.currentHeroBlock.chosenSpell.id;
                    if (this.myHeroes.some(it => it.id === this.currentHero.id)) {
                        Ws.sendMessage('CAST_SPELL', {spellPosition: chosenSpellId, targetPosition: hero.position})
                    }
                }
            },
            getHeroStyle(hero) {
                if (this.gameState === 'MULLIGAN') {
                    if (this.mulliganHeroesIds.includes(hero.id)) {
                        return {'background-color': 'cyan'};
                    }
                }
                if (this.gameState === 'PLAYING') {
                    const chosenSpell = this.$refs.currentHeroBlock.chosenSpell;
                    if (chosenSpell.id && chosenSpell.targetPositions.includes(hero.position)) {
                        return {'background-color': 'yellow'}
                    }
                }
                return {'background-color': 'brown'};
            },
            startGame() {
                Ws.sendMessage('CHOOSE_HEROES', { heroes: this.mulliganHeroesIds });
                this.mulliganHeroesIds = [];
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
        height: 100%;
        width: max-content;
        margin: 0 auto;
    }

    .queueWrapper .queue .heroInQueue {
        float: left;
        width: 50px;
        height: 50px;
        margin: 5px;
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

    .currentHeroBlock {
        background-color: #ffcfec;
    }

    .chosenHeroBlock {
        background-color: #aeb1f2;
    }

    .startButton {
        margin: 10px;
    }
</style>