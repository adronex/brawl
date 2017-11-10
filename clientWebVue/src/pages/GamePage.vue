<template>
    <div class="page">

        <div class="backToMenu">
            <div>
                Login as {{ login }}
            </div>
            <v-link href="/play">Menu</v-link>
        </div>

        <queue :queue="getGameData().queue"></queue>

        <div class="team">
            <div class="ownTeam">
                <div class="hero own"
                     v-for="battleHero in getGameData().ownHeroes"
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
                     v-for="battleHero in getGameData().enemyHeroes"
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
                   :hero="getGameData().currentHero"
                   ref="currentHeroBlock">
        </hero-info>
        <hero-info class="chosenHeroBlock"
                   :hero="getGameData().chosenHero"
                   v-show="getGameData().chosenHero.id">
        </hero-info>

        <div class="battleLogBlock">
            <p v-for="message in getGameData().battleLogMessages">{{message}}</p>
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
                login: Auth.getLogin()
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
            getGameData() {
                return Game.getData();
            },
            handleNotification() {
                this.$forceUpdate();
            },
            onHeroHover(hero) {
                Game.chooseHero(hero);
                this.$forceUpdate();
            },
            onHeroClick(hero) {
                if (Game.getData().gameState === Game.GAME_STATES.MULLIGAN) {
                    Game.addMulliganHero(hero.id);
                }
                if (Game.getData().gameState === Game.GAME_STATES.PLAYING) {
                    const chosenSpellId = this.$refs.currentHeroBlock.chosenSpell.id;
                    Game.castSpell(chosenSpellId, hero);
                }
                this.$forceUpdate();
            },
            getHeroStyle(hero, targetEnemy) {
                if (Game.getData().gameState === Game.GAME_STATES.MULLIGAN) {
                    if (Game.getData().mulliganHeroesIds.includes(hero.id)) {
                        return {'background-color': 'cyan'};
                    }
                }
                if (Game.getData().gameState === Game.GAME_STATES.PLAYING) {
                    const chosenSpell = this.$refs.currentHeroBlock.chosenSpell;
                    if (!Game.getData().currentHero) {
                        return {'background-color': 'brown'};
                    }
                    if (Game.getData().currentHero.id === hero.id) {
                        return {'background-color': 'red'};
                    }
                    if (!chosenSpell.id) {
                        return {'background-color': 'brown'};
                    }
                    if (chosenSpell.config.targetOwnPositions.includes(hero.position) && !targetEnemy) {
                        return {'background-color': 'yellow'}
                    } else if (chosenSpell.config.targetEnemyPositions.includes(hero.position) && targetEnemy) {
                        return {'background-color': 'yellow'}
                    }
                    if (chosenSpell.config.casterPositions.includes(hero.position) && !targetEnemy) {
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