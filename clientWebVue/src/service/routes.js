'use strict';
export default {
    '/squadEdit': 'SquadEditPage',
    '/heroEdit': 'HeroEditPage',
    '/play': 'PlayPage',
    '/game': `GamePage`,
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