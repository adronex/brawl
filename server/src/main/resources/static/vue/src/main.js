const Vue = require('vue/dist/vue.js');
const app =  require('./app.vue').default;

new Vue({
    el: '#app',
    template: '<app/>',
    components: { app }
});