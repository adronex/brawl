<template>
    <div class="hero">
        <div>Name: {{hero.name}}</div>
        <spell v-for="spell in hero.spells"
               :key="spell.id"
               :spellId="spell.id">
        </spell>
    </div>
</template>

<script>

    import StaticData from '../../staticData';
    import Spell from './Spell.vue';

    export default {
        components: {
            Spell
        },
        props: {
            hero: {
                type: Object,
                required: true
            }
        }
    }
</script>

<style>
    .hero {
        float: left;
        background-color: #ff9599;
        width: 50px;
        height: 60px;
        margin-left: 5%;
        margin-top: 5%;
        margin-bottom: 5%;
    }
</style>