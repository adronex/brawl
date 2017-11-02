<template>
    <div class="spell">
        <div class="image"
             :style="cssBackgroundImage">
        </div>
    </div>
</template>

<script>
    'use strict';
    import StaticData from '../../service/staticData.js'

    export default {
        data: function () {
            return {
                spell: {},
                cssBackgroundImage: {}
            }
        },
        props: {
            spellId: {
                type: String,
                required: true
            }
        },
        mounted: function () {
            StaticData.getSpellsPromise().then(it => {
                this.spell = it[this.spellId];
                if (!this.spell) {
                    this.spell = {};
                    console.warn(`Spell static data for id ${this.spellId} is absent.}`)
                }
                this.cssBackgroundImage = StaticData.getSpellBackgroundImageCss(this.spellId);
            });
        }
    }
</script>

<style>
    .spell {
        float: left;
        border: double 1px white;
    }

    .image {
        float: left;
        background-size: 100%;
        width: 100%;
        height: 100%;
    }
</style>