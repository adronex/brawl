<template>
    <main-layout>
        <p>Hero creation or edition screen</p>
        <hero v-for="hero in heroes"
              :key="hero.id"
              :hero="hero">
        </hero>
        <spell-table></spell-table>
    </main-layout>
</template>

<script>
    'use strict';
    import Urls from '../service/urls'
    import Rest from '../service/rest'
    import MainLayout from '../layouts/MenuLayout.vue'
    import Hero from '../components/menu/Hero.vue'
    import SpellTable from '../components/menu/SpellTable.vue'

    export default {
        components: {
            MainLayout,
            Hero,
            SpellTable
        },
        data: function () {
            return {
                heroes: []
            }
        },
        mounted: function () {
            Rest.promisedAuthenticatedRequest(Urls.api.heroes.my).then(it => this.heroes = it);
        }
    }
</script>