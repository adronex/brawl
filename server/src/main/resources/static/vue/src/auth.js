import Config from "./configs";

export default {
    auth,
    logout,
    getLogin
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
        .then(function (response) {
            return response.json();
        })
        .then(function (response) {
            localStorage.setItem("login", login);
            response.claimed_in = new Date().getTime();
            localStorage.setItem("auth_data", JSON.stringify(response));
        }).catch(function (error) {
        alert(`Error: ${error}`
        );
    });
}

function logout() {
    localStorage.removeItem("login");
    localStorage.removeItem("auth_data");
}

function getLogin() {
    return localStorage.getItem("login")
}

function status(response) {
    if (response.status >= 200 && response.status < 300) {
        return Promise.resolve(response)
    } else {
        return Promise.reject(new Error(response.statusText))
    }
}