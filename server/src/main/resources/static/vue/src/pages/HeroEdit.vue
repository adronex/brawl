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
    import MainLayout from '../layouts/Main.vue'
    import HeroInMenu from '../components/HeroInMenu.vue'
    import Config from '../configs.js'
    import Auth from '../auth.js'

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
        return Auth.fetchWithAuth(`${Config.serverURL}/api/heroes/my`).then()
    }
</script>