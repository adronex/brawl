<template>
    <main-layout>
        <p>Squad creation or edition screen</p>
        <div class="squad" v-for="squad in squads">
            <div class="title"> {{squad.name}} </div>
            <div class="heroes">
                <hero-in-menu v-for="hero in squad.heroes"
                              :key="hero.id"
                              :hero="hero">
                </hero-in-menu>
            </div>
        </div>
    </main-layout>
</template>

<script>
    'use strict';
    import MainLayout from '../layouts/MenuLayout.vue';
    import HeroInMenu from '../components/menu/Hero.vue';
    import Urls from '../service/urls';
    import Rest from '../service/rest';

    export default {
        components: {
            MainLayout,
            HeroInMenu
        },
        data: function() {
            return {
                squads: []
            }
        },
        mounted: function () {
            loadSquadsList().then(it => this.squads = it);
        },
        methods: {
        }
    }

    function loadSquadsList() {
        return Rest.promisedAuthenticatedRequest(Urls.api.squads.my)
    }
</script>

<style>
    .squad {
        background-color: #eef1c1;
    }
    .squad .title {
        background-color: #f1dbd1;
        float: left;
        padding: 10px;
    }
    .squad .heroes {
        float: left;
        background-color: #edfffc;
        width: 40%;
        min-width: 100px;
    }
</style>