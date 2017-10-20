'use strict';
import Urls from "./urls";
import Rest from "./rest";

export default {
    auth,
    logout,
    getLogin,
    isAuthenticated
}

function auth(login, password) {
    const requestString = `grant_type=password&username=${login}&password=${password}`;
    return Rest.promisedRequest(Urls.api.login, {
        method: 'POST',
        headers: {
            'Authorization': `Basic ${btoa('Lola:Bola')}`,
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: requestString
    }).then(function (response) {
        localStorage.setItem("login", login);
        response.claimed_in = new Date().getTime();
        localStorage.setItem("auth_data", JSON.stringify(response));
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