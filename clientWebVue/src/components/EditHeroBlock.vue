<template>
    <div class="editHeroBlock">
        <spell-table></spell-table>
        <input class="heroNameInput" type="text" placeholder="Hero name"/>
        <input type="button" value="Submit"/>
    </div>
</template>

<script>
    'use strict';
    import Hero from '../components/menu/Hero.vue'
    import SpellTable from '../components/menu/SpellTable.vue'

    export default {
        components: {
            Hero,
            SpellTable
        },
        data: function () {
            return {
                hero: []
            }
        },
        mounted: function () {
        }
    }
</script>
<style>
    .editHeroBlock {
        float: left;
        margin-left: 10px;
        margin-top: 30px;
    }
    .heroNameInput {
        width: 100px;
    }
</style>