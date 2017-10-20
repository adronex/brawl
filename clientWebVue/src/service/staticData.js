'use strict';
import Urls from "./urls";
import Rest from "./rest";

export default {
    getSpellsPromise,
    getSpellBackgroundImageCssProperty
}

function getSpellsPromise() {
    return Rest.promisedRequest(Urls.api.static.spells);
}

function getSpellBackgroundImageCssProperty(id) {
    let backgroundProperty;
    try {
        backgroundProperty = require(`images/spell/${id}.png`);
    } catch (e) {
        console.warn(`Spell image with id ${id} isn't present in img/spell folder. Placeholder used instead`);
        backgroundProperty = require(`images/placeholder/SPELL.png`);
    }
    return {'background-image': `url(${backgroundProperty})`};
}