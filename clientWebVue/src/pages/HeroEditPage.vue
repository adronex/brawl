<template>
    <main-layout>
        <p>Hero creation or edition screen</p>
        <hero-in-menu v-for="hero in heroes"
                      :key="hero.id"
                      :hero="hero">
        </hero-in-menu>
    </main-layout>
</template>

<script>
    'use strict';
    import MainLayout from '../layouts/MenuLayout.vue'
    import HeroInMenu from '../components/menu/Hero.vue'
    import Urls from '../service/urls'
    import Rest from '../service/rest'

    export default {
        components: {
            MainLayout,
            HeroInMenu
        },
        mounted: function(){
            this.onLoad();
        },
        data: function() {
            return {heroes: []}
        },
        methods: {
            onLoad() {
                loadHeroList().then(it => this.heroes = it);
            }
        }
    }

    function loadHeroList() {
        return Rest.promisedAuthenticatedRequest(Urls.api.heroes.my)
    }
</script>