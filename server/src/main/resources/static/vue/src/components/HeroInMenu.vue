<template>
    <div class="hero">
        <!--<p>Name: hero.name</p>-->
        <div>Name: {{hero.name}}</div>
        <div v-for="spell in hero.spells"
             class="spell">
            <!--<img :src="pathToCat()"/>-->
            <!--Spells: {{hero.spells}}-->
        </div>
        <!--<p>Bodyparts: {{hero.bodyparts}}</p>-->
    </div>
</template>

<script>

    export default {
        props: {
            hero: {
                type: Object,
                required: true
            }
        },
        computed: {

        },
        methods: {
//            pathToCat: function() { return require('./spells/SUCKER_PUNCH.png', true); }
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
    .hero .spell {
        float: left;
        border: solid 1px;
        width: 20px;
        height: 20px;
        /*background-image: url("./spells/SUCKER_PUNCH.img");*/
    }
</style>