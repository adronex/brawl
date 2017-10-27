import Auth from './auth';
import Urls from './urls';

export default {
    isConnected,
    findGame,
    sendMessage,
    subscribeOnMessageEvent
}

let webSocket;
let subscribers = [];

function isConnected() {
    return webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED;
}

function findGame(squadId) {
    if (isConnected()) {
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

function sendMessage(type, body) {
    webSocket.send(JSON.stringify({type: type, body: body }));
}

function subscribeOnMessageEvent(subscriber) {
    subscribers.push(subscriber);
}

function notifyAll(response){
    subscribers.forEach(it => it.handleNotification(response));
}

function handleMessage(responseAsText) {
    let response = JSON.parse(responseAsText);
    notifyAll(response);
}