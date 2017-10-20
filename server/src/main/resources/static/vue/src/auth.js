'use strict';
import Config from "./configs";

export default {
    auth,
    logout,
    getLogin,
    fetchWithAuth,
    fetchWithoutAuth
}

function auth(login, password) {
    const requestString = `grant_type=password&username=${login}&password=${password}`;
    return fetch(`${Config.serverURL}/api/login`, {
        method: 'POST',
        headers: {
            'Authorization': `Basic ${btoa('Lola:Bola')}`,
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: requestString
    }).then(status)
        .then(it => it.json())
        .then(function (response) {
            localStorage.setItem("login", login);
            response.claimed_in = new Date().getTime();
            localStorage.setItem("auth_data", JSON.stringify(response));
        })
        .catch(function (error) {
            alert(`Error: ${error}`);
        });
}

function logout() {
    localStorage.removeItem("login");
    localStorage.removeItem("auth_data");
}

function isAuthenticated() {
    // todo: create refresh token logic.
    return localStorage.getItem('auth_data');
}

function getLogin() {
    return localStorage.getItem("login")
}

function status(response) {
    if (200 <= response.status && response.status < 300) {
        return Promise.resolve(response)
    }
    return Promise.reject(new Error(response.statusText))
}

function fetchWithAuth(url, options) {
    if (isAuthenticated()) {
        if (!options) options = {};
        if (!options.headers) options.headers = {};
        const accessToken = JSON.parse(localStorage.getItem('auth_data'))['access_token'];
        const tokenType = JSON.parse(localStorage.getItem('auth_data'))['token_type'];
        options.headers['Authorization'] = `${tokenType} ${accessToken}`;
        options.headers['Content-Type'] = 'application/json';
    }
    return fetchWithoutAuth(url, options);
}

function fetchWithoutAuth(url, options) {
    return fetch(url, options).then(status).then(it => it.json());
}