<template>
    <div class="container">
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

        <slot></slot>
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

<style scoped>
    .container {
        max-width: 600px;
        margin: 0 auto;
        padding: 15px 30px;
        background: #f9f7f5;
    }
</style>