'use strict';
import Auth from "./auth"

export default {
    promisedAuthenticatedRequest,
    promisedRequest
}

function promisedAuthenticatedRequest(url, options) {
    if (!options) options = {};
    if (Auth.isAuthenticated()) {
        if (!options.headers) options.headers = {};
        const accessToken = JSON.parse(localStorage.getItem('auth_data'))['access_token'];
        const tokenType = JSON.parse(localStorage.getItem('auth_data'))['token_type'];
        options.headers['Authorization'] = `${tokenType} ${accessToken}`;
        options.headers['Content-Type'] = 'application/json';
    }
    return promisedRequest(url, options);
}

function promisedRequest(url, options) {
    if (!options) options = {};
    return fetch(url, options)
        .then(status)
        .then(it => it.json())
        .catch(error => alert(`Error: ${error}`));
}

function status(response) {
    if (200 <= response.status && response.status < 300) {
        return Promise.resolve(response)
    }
    return Promise.reject(new Error(response.statusText))
}