'use strict';
import Urls from "./urls";
import Rest from "./rest";

export default {
    getSpells,
    getSpellBackgroundImageCssProperty
}

let spells = [];

function getSpells() {
    return spells;
}

function onLoad() {
    Rest.promisedRequest(Urls.api.static.spells).then(it => spells = it);
}

onLoad();

function getSpellBackgroundImageCssProperty(id) {
    let backgroundProperty;
    try {
        backgroundProperty = require(`./../assets/img/spell/${id}.png`);
    } catch (e) {
        console.warn(`Spell image with id ${id} isn't present in img/spell folder. Placeholder used instead`);
        backgroundProperty = require(`./../assets/img/placeholder/SPELL.png`);
    }
    return {'background-image': `url(${backgroundProperty})`};
}