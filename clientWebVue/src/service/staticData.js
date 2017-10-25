'use strict';
import Urls from "./urls";
import Rest from "./rest";

export default {
    getSpellsPromise,
    getSpellBackgroundImageCss
}

function getSpellsPromise() {
    return Rest.promisedRequest(Urls.api.static.spells);
}

function getSpellBackgroundImageCss(id) {
    let backgroundProperty;
    try {
        backgroundProperty = require(`images/spells/${id}.png`);
    } catch (e) {
        console.warn(`Spell image with id ${id} isn't present in img/spell folder. Placeholder used instead`);
        backgroundProperty = require(`images/placeholders/SPELL.png`);
    }
    return {'background-image': `url(${backgroundProperty})`};
}