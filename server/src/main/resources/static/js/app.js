'use strict';

let app = angular.module('clockworks', []);

let webSocket;

app.controller('squadMenuController', ['$scope', '$http', function ($scope, $http) {

    $scope.connected = false;
    $scope.gameStage = 'MENU';
    $scope.myHeroes = [];
    $scope.enemyHeroes = [];
    $scope.heroesQueue = [];
    $scope.battleLog = [];
    $scope.messages = [];
    $scope.temp = {};
    $scope.onLoad = function () {
        $http.get('http://localhost:8080/api/squads/my').then(function (response, status) {
            $scope.availableSquads = response.data;
        });
    };

    $scope.openSocket = function () {
        // Ensures only one connection is open at a time
        if (webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED) {
            writeResponse("WebSocket is already opened.");
            return;
        }
        // Create a new instance of the websocket
        webSocket = new WebSocket("ws://localhost:8080/game");

        /**
         * Binds functions to the listeners for the websocket.
         */
        webSocket.onopen = function (event) {
            let squads = document.getElementsByName('squad');
            let chosenSquad = $scope.temp.chosenSquadId;

            webSocket.send(JSON.stringify({type: 'INITIAL', body: {squadId: chosenSquad}}));
            $scope.gameStage = 'GAME';
            $scope.$digest();
        };

        webSocket.onmessage = function (event) {
            let response = JSON.parse(event.data);
            if (response.queue) {
                $scope.heroesQueue = response.queue;
                delete response.queue;
            }
            if (response.battleLog) {
                $scope.battleLog = response.battleLog;
                delete response.battleLog;
            }
            if (response.connected) {
                $scope.connected = true;
                delete response.connected;
            }
            if (response.myHeroes) {
                $scope.myHeroes = response.myHeroes;
                delete response.myHeroes;
            }
            if (response.enemyHeroes) {
                $scope.enemyHeroes = response.enemyHeroes;
                delete response.enemyHeroes;
            }
            if (!(Object.keys(response).length === 0 && response.constructor === Object)) {
                $scope.messages.push(response);
            }
            $scope.$digest();
        };

        webSocket.onclose = function (event) {
            $scope.connected = false;
            $scope.gameStage = 'MENU';
        };
    };

    /**
     * Sends the value of the text input to the server
     */
    $scope.sendMulliganInfo = function () {
        webSocket.send(JSON.stringify({type: 'CHOOSE_HEROES', body: {heroes: ['2', '1']}}));
    };

    $scope.sendFirstSpell = function () {
        webSocket.send(JSON.stringify({type: 'CAST_SPELL', body: {target: 1, forEnemy: true}}));
    };

    $scope.closeSocket = function () {
        webSocket.close();
    };

    function writeResponse(text) {
        document.getElementById('messages').messagesDiv.innerHTML += "<br/>" + text;
    }
}]);