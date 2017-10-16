<template>
    <main-layout>
        <p>Hero creation or edition screen</p>
    </main-layout>
</template>

<script>
    import MainLayout from '../layouts/Main.vue'
    import config from '../configs.js'

    export default {
        components: {
            MainLayout
        },
        mounted: function(){
            this.onLoad();
        },
        methods: {
            onLoad() {
                this.$http.get(config.serverURL + 'api/static/spells').then((response) => {
                    //success
                    console.log(response)
                }, (response) => {
                    //error
                    console.log(response)
                });
            }
        }
    }
</script>