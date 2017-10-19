<template>
    <div>
        <div class="menu">
            <auth-block></auth-block>
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
    import AuthBlock from '../components/AuthBlock.vue'

    export default {
        components: {
            VLink,
            AuthBlock
        }
    }
</script>

<style>
    .menu {
        min-width: 150px;
        min-height: 200px;
        background: #f9f7f5;
        float: left;
    }
    .content {
        min-width: 150px;
        min-height: 200px;
        width: 30%;
        height: auto;
        background-color: antiquewhite;
        float: left;
    }
</style>