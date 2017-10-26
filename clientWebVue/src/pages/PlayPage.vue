<template>
    <main-layout>
        <input type="button" value="Go" v-on:click="findGame()"/>
    </main-layout>
</template>

<script>
    'use strict';
    import Game from '../service/ws'
    import Rest from '../service/rest'
    import Urls from '../service/urls'
    import Routes from '../service/routes'
    import MainLayout from '../layouts/MenuLayout.vue'

    export default {
        components: {
            MainLayout
        },
        methods: {
            findGame() {
                Rest.promisedAuthenticatedRequest(Urls.api.squads.my).then(it => {
                    Game.findGame(it[0].id);
                    Routes.go(this, '/battlefield');
                });
            }
        }
    }
</script>

<style>
</style>