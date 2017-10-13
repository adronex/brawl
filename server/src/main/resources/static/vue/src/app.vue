<template>
    <div id="app">
        <p>{{ greeting }} hui</p>
    </div>
</template>

<script>
    module.exports = {
        ready: function () {
            this.getSpells;
        },
        data: {
            spells: []
        },
        methods: {
            getSpells: function () {
                this.$http.get('/api/spells/')
                    .success(function(counties){
                        this.counties = counties;
                    })
                    .error(function(){

                    });
            }
        }
    };
</script>

<style>
</style>