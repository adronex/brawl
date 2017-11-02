'use strict';
import Ws from './ws';

const GAME_STATES = {
    UNKNOWN: 'UNKNOWN',
    MULLIGAN: 'MULLIGAN',
    PLAYING: 'PLAYING'
};

const MESSAGE_TYPES = {
    INITIAL: 'INITIAL',
    CHOOSE_HEROES: 'CHOOSE_HEROES',
    CAST_SPELL: 'CAST_SPELL'
};

let subscribers = [];

let mulliganHeroesIds = [];
let currentHero = {};

let gameState = GAME_STATES.UNKNOWN;
let ownHeroes = [];
let enemyHeroes = [];
let queue = [];

export default {
    GAME_STATES,
    data: {
        mulliganHeroesIds,
        currentHero,

        gameState,
        ownHeroes,
        enemyHeroes,
        queue
    },

    subscribe, // publisher
    handleNotification, // as subscriber
    isConnectionEstablished,
    findGame,
    addMulliganHero,
    submitMulliganHeroes,
    castSpell
};

function handleNotification(notification) {
    if (notification.ownHeroes) {
        ownHeroes = notification.ownHeroes;
        console.log(ownHeroes);
        delete notification.ownHeroes;
    }
    if (notification.enemyHeroes) {
        enemyHeroes = notification.enemyHeroes;
        console.log(enemyHeroes);
        delete notification.enemyHeroes;
    }
    if (notification.gameState) {
        gameState = notification.gameState;
        delete notification.gameState;
    }
    if (notification.queue) {
        queue = notification.queue;
        if (this.queue.length > 0) {
            currentHero = this.ownHeroes.find(it => it.id === this.queue[0].id);
            if (!currentHero) {
                currentHero = this.enemyHeroes.find(it => it.id === this.queue[0].id);
            }
        }
        delete notification.queue;
    }
    notifySubscribers();
}

function isConnectionEstablished() {
    return Ws.isConnected();
}

function subscribe(subscriber) {
    Ws.subscribeOnMessageEvent(this);
    subscribers.push(subscriber);
}

function notifySubscribers() {
    subscribers.forEach(it => it.handleNotification())
}

function findGame(squadId) {
    Ws.openConnection(MESSAGE_TYPES.INITIAL, squadId)
}

function addMulliganHero(heroId) {
    if (mulliganHeroesIds.includes(heroId)) {
        mulliganHeroesIds = mulliganHeroesIds.filter(item => item !== heroId);
    } else {
        mulliganHeroesIds.push(heroId);
    }
    if (mulliganHeroesIds.length > 4) {
        mulliganHeroesIds = mulliganHeroesIds.slice(1);
    }
}

function submitMulliganHeroes() {
    Ws.sendMessage(MESSAGE_TYPES.CHOOSE_HEROES, {heroes: mulliganHeroesIds});
    mulliganHeroesIds = [];
}

function castSpell(spellId, hero) {
    const targetEnemy = !ownHeroes.some(it => it.id === hero.id);
    Ws.sendMessage(MESSAGE_TYPES.CAST_SPELL, {
        spellId,
        targetPosition: hero.position,
        targetEnemy: targetEnemy
    })
}