<template>
    <div>
        <!--<p>Name: hero.name</p>-->
        <div>Name: {{hero.name}}</div>
        <div v-for="spell in hero.spells"
             :style="getImageUrl(spell.id)"
             class="spell">
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
        methods: {
            getImageUrl: function (id) {
                let backgroundProperty;
                try {
                    backgroundProperty = require(`../assets/img/spell/${id}.png`);
                } catch (e) {
                    console.warn(`Spell image with id ${id} isn't present in img/spell folder. Placeholder used instead`);
                    backgroundProperty = require(`../assets/img/placeholder/SPELL.png`);
                }
                return {'background-image': `url(${backgroundProperty})`};
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
    .hero .spell {
        float: left;
        border: double 1px white;
        width: 20px;
        height: 20px;
        /*background: url("../assets/img/SUCKER_PUNCH.png");*/
        background-size: 100%;
    }
    .hero .spell img {

    }
</style>