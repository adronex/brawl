'use strict';

let app = angular.module('clockworks', []);

let webSocket;

app.controller('squadMenuController', ['$scope', '$http', function ($scope, $http) {

    const serverUrl = 'localhost:8082';
    $scope.staticData = {};

    let init = function () {
        $scope.connected = false;
        $scope.currentWindow = 'MENU';

        $scope.mulliganChosenHeroesIds = [];
        $scope.myHeroes = [];
        $scope.enemyHeroes = [];
        $scope.heroesQueue = [];
        $scope.battleLog = [];
        $scope.messages = [];
        $scope.temp = {};
        $scope.newHero = {};
        $scope.heroSpells = [];
    };

    init();

    $scope.onLoad = function () {
        $http.get('http://' + serverUrl + '/api/squads/my').then(function (response, status) {
            $scope.availableSquads = response.data;
        });
        $http.get('http://' + serverUrl + '/api/static/spells').then(function (response, status) {
            $scope.staticData.spells = response.data;
        });
        $http.get('http://' + serverUrl + '/api/static/bodyparts').then(function (response, status) {
            $scope.staticData.bodyparts = response.data;
        });
    };

    $scope.openSocket = function () {
        // Ensures only one connection is open at a time
        if (webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED) {
            writeResponse("WebSocket is already opened.");
            return;
        }
        // Create a new instance of the websocket
        webSocket = new WebSocket('ws://' + serverUrl + '/game');

        /**
         * Binds functions to the listeners for the websocket.
         */
        webSocket.onopen = function (event) {
            let squads = document.getElementsByName('squad');
            let chosenSquad = $scope.temp.chosenSquadId;

            webSocket.send(JSON.stringify({type: 'INITIAL', body: {squadId: chosenSquad}}));
            $scope.currentWindow = 'GAME';
            $scope.$digest();
        };

        webSocket.onmessage = function (event) {
            let response = JSON.parse(event.data);
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
                $scope.myHeroes.forEach(h => {
                    h.appearance = {};
                    h.appearance.head = getRandomColor();
                    h.appearance.body = getRandomColor();
                    h.appearance.arms = getRandomColor();
                    h.appearance.legs = getRandomColor()
                });
                delete response.myHeroes;
            }
            if (response.enemyHeroes) {
                $scope.enemyHeroes = response.enemyHeroes.slice().reverse();
                $scope.enemyHeroes.forEach(h => {
                    h.appearance = {};
                    h.appearance.head = getRandomColor();
                    h.appearance.body = getRandomColor();
                    h.appearance.arms = getRandomColor();
                    h.appearance.legs = getRandomColor()
                });
                delete response.enemyHeroes;
            }
            if (response.gameState) {
                $scope.gameState = response.gameState;
                delete response.gameState;
            }
            if (response.heroSpells) {
                $scope.heroSpells = response.heroSpells;
                delete response.heroSpells;
            }
            if (response.queue) {
                $scope.heroesQueue = response.queue;
                if (!$scope.heroesQueue[0].enemy) {
                    $scope.currentSpells = $scope.heroSpells[$scope.heroesQueue[0].id]
                }
                delete response.queue;
            }
            if (!(Object.keys(response).length === 0 && response.constructor === Object)) {
                $scope.messages.push(response);
            }
            $scope.$digest();
        };

        webSocket.onclose = function (event) {
            $scope.connected = false;
            $scope.currentWindow = 'MENU';
            init();
            $scope.$digest();
        };
    };

    $scope.onHeroClick = function(heroId, enemy) {
        if ($scope.gameState === 'MULLIGAN') {
            chooseHeroInMulligan(heroId)
        } else if ($scope.gameState === 'PLAYING' && angular.isNumber($scope.temp.castedSpellPosition)) {
            let heroPosition = enemy
                ? $scope.enemyHeroes.findIndex(hero => hero.id === heroId) + 1
                : -$scope.myHeroes.findIndex(hero => hero.id === heroId) - 1;
            sendSpellToServer($scope.temp.castedSpellPosition, heroPosition);
            delete $scope.temp.castedSpellPosition;
        }
    };

    let chooseHeroInMulligan = function (heroId) {
        if ($scope.mulliganChosenHeroesIds.includes(heroId)) {
            $scope.mulliganChosenHeroesIds = $scope.mulliganChosenHeroesIds.filter(item => item !== heroId);
        } else {
            $scope.mulliganChosenHeroesIds.push(heroId);
        }
        if ($scope.mulliganChosenHeroesIds.length > 4) {
            $scope.mulliganChosenHeroesIds = $scope.mulliganChosenHeroesIds.slice(1);
        }
    };

    /**
     * Sends the value of the text input to the server
     */
    $scope.sendMulliganInfo = function () {
        if ($scope.mulliganChosenHeroesIds.length !== 4) {
            $scope.messages.push('Wrong heroes count');
        } else {
            webSocket.send(JSON.stringify({type: 'CHOOSE_HEROES', body: {heroes: $scope.mulliganChosenHeroesIds}}));
            $scope.mulliganChosenHeroesIds = [];
        }
    };

    $scope.onSpellButtonPress = function (index) {
        let castedSpell = $scope.currentSpells[index];
        if (castedSpell) {
            $scope.temp.castedSpellPosition = index;
        } else {
            console.error('Invalid spell id');
        }
    };

    let sendSpellToServer = function (spellPosition, targetPosition) {
        webSocket.send(JSON.stringify({type: 'CAST_SPELL', body: {spellPosition: spellPosition, targetPosition: targetPosition}}));
    };

    $scope.closeSocket = function () {
        webSocket.close();
    };

    $scope.toNewHeroMenu = function () {
        $scope.newHero = {};
        $scope.currentWindow = 'NEW_HERO'
    };

    $scope.toMainMenu = function () {
        $scope.currentWindow = 'MENU'
    };

    function writeResponse(text) {
        document.getElementById('messages').messagesDiv.innerHTML += "<br/>" + text;
    }

    function getRandomColor() {
        const letters = '0123456789ABCDEF';
        let color = '#';
        for (let i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
    }

    $scope.colors = [
        "#339E42",
        "#039BE5",
        "#EF6C00",
        "#A1887F",
        "#607D8B",
        "#039BE5",
        "#009688",
        "#536DFE",
        "#AB47BC",
        "#E53935",
        "#3F51B5"
    ];
}]);