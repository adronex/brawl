'use strict';
import Auth from './auth';
import Urls from './urls';

export default {
    isConnected,
    openConnection,
    sendMessage,
    subscribeOnMessageEvent
}

let webSocket;
let subscribers = [];

function isConnected() {
    return webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED;
}

function openConnection(type, body) {
    if (isConnected()) {
        throw new Error("WebSocket is already opened.");
    }
    webSocket = new WebSocket(`${Urls.ws.game}?access_token=${Auth.getAccessToken()}`);
    webSocket.onopen = function (event) {

        sendMessage(type, body);
    };

    webSocket.onmessage = function (response) {
        notifySubscribers(response.data);
    }
}

function sendMessage(type, body) {
    webSocket.send(JSON.stringify({type: type, body: body }));
}

function subscribeOnMessageEvent(subscriber) {
    subscribers.push(subscriber);
}

function notifySubscribers(responseAsText) {
    let response = JSON.parse(responseAsText);
    subscribers.forEach(it => it.handleNotification(response));
}