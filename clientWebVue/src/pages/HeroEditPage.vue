<template>
    <main-layout>
        <div class="page">
            <p>Hero creation or edition screen</p>
            <div v-for="hero in heroes"
                 class="hero">
                <div class="buttons">
                    <input type="button"
                           value="Edit"
                           v-on:click="editHero(hero)"/>
                    <input type="button"
                           value="Delete"
                           v-on:click="deleteHero(hero)"/>
                </div>
                <hero :hero="hero">
                </hero>
            </div>
        </div>
        <div v-if="!isEditing">
            <input type="button" value="New" v-on:click="createHero()"/>
        </div>
        <div v-else>
            <submit-hero-block :oldHero="heroToSubmit"></submit-hero-block>
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
    import SubmitHeroBlock from '../components/SubmitHeroBlock.vue'

    export default {
        components: {
            MainLayout,
            Hero,
            SubmitHeroBlock
        },
        data: function () {
            return {
                heroes: [],
                heroToSubmit: {},
                isEditing: false
            }
        },
        mounted: function () {
            Rest.promisedAuthenticatedRequest(Urls.api.heroes.my).then(it => this.heroes = it);
        },
        methods: {
            createHero() {
                this.heroToSubmit = {};
                this.heroToSubmit.spells = [];
                this.isEditing = true;
            },
            editHero(hero) {
                this.isEditing = false;
                // todo: find vue solution
                setTimeout(() => {
                    this.heroToSubmit = hero;
                    this.isEditing = true;
                }, 1);
            },
            deleteHero(hero) {
                console.log('pisya' + hero.id);
            }
        }
    }
</script>

<style>
    .page {
        float: left;
    }
    .page .buttons {
        float: left;
    }
    .page .hero {
        min-width: 50px;
    }
</style>