<template>
    <div class="spell"
         v-on:mouseover="mouseHovered = true"
         v-on:mouseleave="mouseHovered = false">
        <div class="image"
             :style="cssProperty">
        </div>
        <div class="popup" v-show="mouseHovered">
            <div class="icon"
                 :style="cssProperty"></div>
            <div class="info">
                <p>id: {{spell.id}}</p>
                <p>Positions: {{spell.casterPositions}}</p>
                <p>Targets: {{spell.targetPositions}}</p>
            </div>
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
                cssProperty: {},
                mouseHovered: false
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
                this.cssProperty = StaticData.getSpellBackgroundImageCssProperty(this.spellId);
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
        width: 20px;
        height: 20px;
    }

    .popup {
        position: absolute;
        background-color: rgb(87, 84, 86);
        width: 260px;
        height: 100px;
        margin-left: 20px;
        margin-top: 20px;
    }

    .popup .icon {
        float: left;
        border: double 1px white;
        width: 50px;
        height: 50px;
        background-size: 100%;
        margin-left: 5%;
        margin-top: 5%;
    }

    .popup .info {
        float: left;
        width: 65%;
        margin-top: 5%;
        margin-left: 5%;
        color: #e4d8a0;
    }

    .popup .info p {
        margin: 0;
    }
</style>