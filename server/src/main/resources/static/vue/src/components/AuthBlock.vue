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
    import Auth from '../auth'
    import Vue from 'vue'

    export default {
        methods: {
            auth() {
                const login = this.$refs.login.value;
                const password = this.$refs.password.value;
                const vm = this;
                Auth.auth(login, password).then(function () {
                    vm.$forceUpdate();
                });
            },
            logout() {
                Auth.logout();
                this.$forceUpdate();
            },
            getLogin() {
                return Auth.getLogin();
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