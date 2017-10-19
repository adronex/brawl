import Vue from 'vue'
import routes from './routes'
import VueResource from 'vue-resource'

Vue.use(VueResource);

const app = new Vue({
    el: '#app',
    data: {
        currentRoute: window.location.pathname
    },
    computed: {
        ViewComponent () {
            const matchingView = routes[this.currentRoute];
            return matchingView
                ? require('./pages/' + matchingView + '.vue').default
                : require('./pages/404.vue').default
        }
    },
    render (h) {
        return h(this.ViewComponent)
    }
});

window.addEventListener('popstate', () => {
    app.currentRoute = window.location.pathname
});

Vue.http.interceptors.push((request, next)  => {
    request.headers['Authorization'] = auth.getAuthHeader();
    next((response) => {
        if(response.status === 401 ) {
            auth.logout();
            router.go('/login?unauthorized=1');
        }
    });
});