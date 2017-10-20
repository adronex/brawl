'use strict';
const serverUrl = 'http://localhost:8082';
export default {
    serverUrl,
    api: {
        login: `${serverUrl}/api/login`,
        heroes: {
            my: `${serverUrl}/api/heroes/my`
        },
        squads: {
            my: `${serverUrl}/api/squads/my`
        },
        static: {
            spells: `${serverUrl}/api/static/spells`
        }
    }
}