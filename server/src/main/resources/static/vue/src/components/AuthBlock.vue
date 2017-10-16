<template>
    <div class="auth-block">
        <form>
            <input type="text" placeholder="Login" ref="login"/>
            <input type="password" placeholder="Password" ref="password"/>
            <input type="button" value="submit" v-on:click="auth()"/>
        </form>
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
                let requestString = `grant_type=password&login=${login}&password=${password}`;
                this.$http.headers.common['Authorization'] = btoa('Lola:Bola');
                this.$http.post(`${config.serverURL}api/login`, requestString)
                    .then(
                        function (response) {
                            console.log(response);
                        },
                        function (response) {
                            console.error(response);
                        }
                    );
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