'use strict';
const lobbyProtocol = 'http';
const lobbyHost = 'localhost';
const lobbyPort = '8082';

const gameProtocol = 'ws';
const gameHost = 'localhost';
const gamePort = '8082';

const lobbyUrl = `${lobbyProtocol}://${lobbyHost}:${lobbyPort}`;
const gameUrl = `${gameProtocol}://${gameHost}:${gamePort}`;

export default {
    api: {
        login: `${lobbyUrl}/api/login`,
        heroes: {
            my: `${lobbyUrl}/api/heroes/my`,
            submit: `${lobbyUrl}/api/heroes/submit`,
            delete: function (id) {
                return `${lobbyUrl}/api/heroes/delete/${id}`
            }
        },
        squads: {
            my: `${lobbyUrl}/api/squads/my`
        },
        static: {
            spells: `${lobbyUrl}/api/static/spells`
        }
    },
    ws: {
        game: `${gameUrl}/game`
    }
}