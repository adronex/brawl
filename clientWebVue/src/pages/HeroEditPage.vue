<template>
    <main-layout>
        <div class="page">
            <p>Hero creation or edition screen</p>
            <hero v-for="hero in heroes"
                  :key="hero.id"
                  :hero="hero">
            </hero>
        </div>
        <div v-if="!isEditing">
            <input type="button" value="New" v-on:click="isEditing = true"/>
        </div>
        <div v-else>
            <new-hero-block></new-hero-block>
            <input type="button" value="Close" v-on:click="isEditing = false"/>
        </div>
    </main-layout>
</template>

<script>
    'use strict';
    import Urls from '../service/urls'
    import Rest from '../service/rest'
    import MainLayout from '../layouts/MenuLayout.vue'
    import Hero from '../components/menu/Hero.vue'
    import NewHeroBlock from '../components/EditHeroBlock.vue'

    export default {
        components: {
            MainLayout,
            Hero,
            NewHeroBlock
        },
        data: function () {
            return {
                heroes: [],
                isEditing: false
            }
        },
        mounted: function () {
            Rest.promisedAuthenticatedRequest(Urls.api.heroes.my).then(it => this.heroes = it);
        }
    }
</script>

<style>
    .page {
        float: left;
    }
</style>