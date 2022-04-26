import Vue from 'vue'
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

import {presentStore} from "@/store/modules/presentStore.js";

export default new Vuex.Store({
	modules: {
		presentStore,
	},
	plugins: [createPersistedState(
    {
        Storage : sessionStorage,
    }
    )],
});