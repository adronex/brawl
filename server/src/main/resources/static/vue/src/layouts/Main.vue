<template>
    <div>
        <div class="menu">
            <div class="auth-block">
                <input type="text" placeholder="Login"/>
                <input type="password" placeholder="Password"/>
                <input type="button" value="submit"/>
            </div>
            <ul>
                <li>
                    <v-link href="/squadEdit">Edit squad</v-link>
                </li>
                <li>
                    <v-link href="/heroEdit">Edit hero</v-link>
                </li>
                <li>
                    <v-link href="/battlefield">Play</v-link>
                </li>
            </ul>
        </div>
        <div class="content">
            <slot></slot>
        </div>
    </div>
</template>

<script>
    import VLink from '../components/Link.vue'

    export default {
        components: {
            VLink
        }
    }
</script>

<style>
    .auth-block {
        max-width: 135px;
        border: thin solid;
    }
    .menu {
        min-width: 150px;
        min-height: 200px;
        background: #f9f7f5;
        float: left;
    }
    .content {
        min-width: 150px;
        min-height: 200px;
        background-color: antiquewhite;
        float: left;
    }
</style>