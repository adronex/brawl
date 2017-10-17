<template>
    <div class="auth-block" v-if="!getLogin()">
        <form>
            <input type="text" placeholder="Login" ref="login"/>
            <input type="password" placeholder="Password" ref="password"/>
            <input type="button" value="submit" v-on:click="auth()"/>
        </form>
    </div>
    <div v-else>
        <p>Login as {{getLogin()}}</p>
        <input type="button" value="logout" v-on:click="logout()"/>
    </div>
</template>

<script>
    import config from '../configs'
    import Vue from 'vue'

    export default {
        methods: {
            auth() {
                const login = this.$refs.login.value;
                const password = this.$refs.password.value;
                const requestString = `grant_type=password&username=${login}&password=${password}`;
                this.$http.post(`${config.serverURL}/api/login`, requestString, {
                    headers: {
                        'Authorization': `Basic ${btoa('Lola:Bola')}`,
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                })
                    .then(
                        function (response) {
                            localStorage.setItem("login", login);
                            response.body.claimed_in = new Date().getTime();
                            localStorage.setItem("auth_data", JSON.stringify(response.body));
                        },
                        function (response) {
                            alert(`Error: ${response.body.error}, description: ${response.body.error_description}`);
                        }
                    );
            },
            logout() {
                localStorage.removeItem("login");
                localStorage.removeItem("auth_data");
            },
            getLogin() {
                return localStorage.getItem("login")
            }
        }
    }
</script>

<style>
    .auth-block {
        max-width: 135px;
        border: thin solid;
    }
</style>