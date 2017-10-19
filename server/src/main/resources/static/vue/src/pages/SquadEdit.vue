<template>
    <main-layout>
        <p>Squad creation or edition screen</p>
        <div class="squad" v-for="squad in squads">
            <div class="title"> {{squad.name}} </div>
            <div class="heroes">
                <hero-in-menu class="hero"
                              v-for="hero in squad.heroes"
                              :key="hero.id"
                              :hero="hero">
                </hero-in-menu>
            </div>
        </div>
    </main-layout>
</template>

<script>
    import MainLayout from '../layouts/Main.vue'
    import HeroInMenu from '../components/HeroInMenu.vue'
    import Config from '../configs'
    import Auth from '../auth'

    export default {
        components: {
            MainLayout,
            HeroInMenu
        },
        data: function() {
            return {squads: []}
        },
        mounted: function () {
            loadHeroList().then(it => this.squads = it);
        },
        methods: {
        }
    }

    function loadHeroList() {
        return Auth.fetchWithAuth(`${Config.serverURL}/api/squads/my`, {}).then()
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