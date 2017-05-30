'use strict';

let app = angular.module('clockworks', []);

let webSocket;

app.controller('squadMenuController', ['$scope', '$http', function ($scope, $http) {

    $scope.temp = {};//chosenSquadId = 100500;
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
            document.getElementById('menu').style.display = 'none';
            document.getElementById('game').style.display = 'block';
        };

        webSocket.onmessage = function (event) {
            let response = JSON.parse(event.data);
            if (response.heroesQueue) {
                updateQueue(response.heroesQueue);
                updateGameState(response.gameState, response.winner);
            } else {
                writeResponse(event.data);
            }
        };

        webSocket.onclose = function (event) {
            writeResponse("Connection closed");
            document.getElementById('menu').style.display = 'block';
            document.getElementById('game').style.display = 'none';
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
        messagesDiv.innerHTML += "<br/>" + text;
    }

    function updateQueue(queue) {
        queueDiv.innerHTML = queue.reduce(function (acc, val) {
            return acc + ' <- ' + val.name + '(' + val.health + ')' + (val.enemy ? '(enemy)' : '(you)');
        }, '');
    }

    function updateGameState(state, winner) {
        let text = state;
        if (text === 'END') {
            text += ', ';
            text += winner ? "YOU WIN!" : "YOU LOSE!";
        }
        gameStateDiv.innerHTML = text;
    }
}]);