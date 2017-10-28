'use strict';
import Urls from "./urls";
import Rest from "./rest";

export default {
    getSpellsPromise,
    getSpellBackgroundImageCss,
    getBodypartCss
}

function getSpellsPromise() {
    return Rest.promisedRequest(Urls.api.static.spells);
}

function getSpellBackgroundImageCss(id) {
    let imageUrl;
    try {
        imageUrl = require(`images/spells/${id}.png`);
    } catch (e) {
        console.warn(`Spell image with id ${id} isn't present in img/spell folder. Placeholder used instead`);
        imageUrl = require(`images/placeholders/SPELL.png`);
    }
    return {'background-image': `url(${imageUrl})`};
}

function getBodypartCss(id) {
    let headSvgPath = require(`vectors/bodyparts/${id}.svg`);
    return {
        'background-image': `url(${headSvgPath})`
    };
}