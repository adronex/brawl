import Auth from './auth';
import Urls from './urls';

let webSocket;
let battleLogMessages = [];
let subscribers = [];

export default {
    findGame,
    getBattleLog,
    subscribe
}

function findGame(squadId) {
    if (webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED) {
        throw new Error("WebSocket is already opened.");
    }
    webSocket = new WebSocket(`${Urls.ws.game}?access_token=${Auth.getAccessToken()}`);
    webSocket.onopen = function (event) {

        webSocket.send(JSON.stringify({type: 'INITIAL', body: {squadId: squadId}}));
    };

    webSocket.onmessage = function (response) {
        handleMessage(response.data);
    }
}

function getBattleLog() {
    return battleLogMessages;
}

function subscribe(subscriber) {
    subscribers.push(subscriber);
}

function notifyAll(notification){
    subscribers.forEach(it => it(notification));
}

function handleMessage(responseAsText) {
    console.log(responseAsText);
    let response = JSON.parse(responseAsText);
    if (response.battleLog) {
        handleBattleLogObject(response.battleLog)
    }
    notifyAll(response);
}

function handleBattleLogObject(battleLog) {
    battleLogMessages.push(battleLog);
    console.log(battleLog);
}