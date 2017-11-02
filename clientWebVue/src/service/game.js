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
let battleLogMessages = [];

export default {
    GAME_STATES,
    data: {
        mulliganHeroesIds,
        currentHero,

        gameState,
        ownHeroes,
        enemyHeroes,
        queue,
        battleLogMessages
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
    // IGNORED PROPERTIES because Vuejs doesn't support 'key = undefined' or 'delete key'
    let ignoredProperties = [];
    if (notification.ownHeroes) {
        ownHeroes = notification.ownHeroes;
        ignoredProperties.push('ownHeroes');
    }
    if (notification.enemyHeroes) {
        enemyHeroes = notification.enemyHeroes;
        ignoredProperties.push('enemyHeroes');
    }
    if (notification.gameState) {
        gameState = notification.gameState;
        ignoredProperties.push('gameState');
    }
    if (notification.queue) {
        queue = notification.queue;
        ignoredProperties.push('queue');
        if (queue.length > 0) {
            currentHero = ownHeroes.find(it => it.id === queue[0].id);
            if (!currentHero) {
                currentHero = enemyHeroes.find(it => it.id === queue[0].id);
            }
        }
    }
    Object.keys(notification).forEach(key => {
        if (ignoredProperties.indexOf(key) === -1) {
            const message = {
                [key]: notification[key]
            };
            battleLogMessages.push(message);
        }
    });
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
    Ws.openConnection(MESSAGE_TYPES.INITIAL, {squadId})
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