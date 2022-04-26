import Vue from 'vue'
import App from './App.vue'
import router from "./router";
import store from "./store";
import vuetify from './plugins/vuetify'
import FontAwesomeIcon from "@fortawesome/vue-fontawesome";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core"
import {faHome, faSearch, faPlus } from "@fortawesome/free-solid-svg-icons"

faLibrary.add(faHome, faSearch, faPlus)
Vue.component("font-awesome-icon", FontAwesomeIcon);

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
