<template>
    <div class="page">

        <div class="backToMenu">
            <div>
                Login as {{ login }}
            </div>
            <v-link href="/play">Menu</v-link>
        </div>

        <queue :queue="queue"></queue>

        <div class="team">
            <div class="ownTeam">
                <div class="hero own"
                     v-for="battleHero in ownHeroes"
                     v-on:click="onHeroClick(battleHero)"
                     v-on:mouseover="onHeroHover(battleHero)"
                     :style="getHeroStyle(battleHero, false)">
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
                     :style="getHeroStyle(battleHero, true)"></div>
            </div>
        </div>

        <div class="startButton">
            <input type="button"
                   value="Start"
                   v-on:click="onStartButtonClick()"/>
        </div>

        <hero-info class="currentHeroBlock"
                   :hero="currentHero"
                   ref="currentHeroBlock">
        </hero-info>
        <hero-info class="chosenHeroBlock"
                   :hero="chosenHero"
                   v-show="chosenHero.id">
        </hero-info>

        <div class="battleLogBlock">
            <p v-for="message in battleLogMessages">{{message}}</p>
        </div>
    </div>
</template>

<script>
    'use strict';
    import Routes from '../service/routes'
    import Auth from '../service/auth'
    import Game from '../service/game'

    import VLink from '../components/Link.vue'
    import Spell from '../components/game/Spell.vue'
    import Queue from '../components/game/Queue.vue'
    import HeroInfo from '../components/game/HeroInfo.vue'

    export default {
        components: {
            VLink,
            Spell,
            Queue,
            HeroInfo
        },
        data: function () {
            return {
                login: Auth.getLogin(),
                battleLogMessages: [],
                queue: [],
                ownHeroes: [],
                enemyHeroes: [],
                currentHero: {},
                chosenHero: {},
                gameState: {}
            }
        },
        mounted: function () {
            if (!Game.isConnectionEstablished()) {
                Routes.go(this, '/play');
                console.log('Websocket connection lost. Redirecting to play screen.');
            }
            Game.subscribe(this);
        },
        methods: {
            handleNotification() {
                this.gameState = Game.data.gameState;
                this.ownHeroes = Game.data.ownHeroes;
                this.enemyHeroes = Game.data.enemyHeroes;
                this.queue = Game.data.queue;
            },
            onHeroHover(hero) {
                this.chosenHero = hero;
            },
            onHeroClick(hero) {
                if (Game.gameState === Game.GAME_STATES.MULLIGAN) {
                    Game.addMulliganHero(hero.id)
                }
                if (Game.gameState === Game.GAME_STATES.PLAYING) {
                    const chosenSpellId = this.$refs.currentHeroBlock.chosenSpell.id;
                    Game.castSpell(chosenSpellId, hero);
                }
            },
            getHeroStyle(hero, targetEnemy) {
                if (Game.gameState === Game.GAME_STATES.MULLIGAN) {
                    if (Game.mulliganHeroesIds.includes(hero.id)) {
                        return {'background-color': 'cyan'};
                    }
                }
                if (Game.gameState === Game.GAME_STATES.PLAYING) {
                    const chosenSpell = this.$refs.currentHeroBlock.chosenSpell;
                    if (Game.currentHero.id === hero.id) {
                        return {'background-color': 'red'};
                    }
                    if (!chosenSpell.id) {
                        return {'background-color': 'brown'};
                    }
                    if (chosenSpell.targetOwnPositions.includes(hero.position) && !targetEnemy) {
                        return {'background-color': 'yellow'}
                    } else if (chosenSpell.targetEnemyPositions.includes(hero.position) && targetEnemy) {
                        return {'background-color': 'yellow'}
                    }
                    if (chosenSpell.casterPositions.includes(hero.position) && !targetEnemy) {
                        return {'background-color': 'blue'}
                    }
                }
                return {'background-color': 'brown'};
            },
            onStartButtonClick() {
                Game.submitMulliganHeroes();
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

    .team {
        display: inline-block;
        float: left;
        width: 50%;
        height: 150px;
        margin-top: 20px;
        margin-bottom: 50px;
    }

    .team .ownTeam {
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

    .team .hero.own {
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