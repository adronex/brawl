'use strict';
export default {
    '/squadEdit': 'SquadEditPage',
    '/heroEdit': 'HeroEditPage',
    '/play': 'PlayPage',
    '/battlefield': `BattlefieldPage`,
    go
}

function go(viewModel, href) {
    viewModel.$root.currentRoute = href;
    window.history.pushState(
        null,
        this[href],
        href
    )
}