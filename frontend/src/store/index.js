import { createStore } from "vuex";
import {presentStore} from "@/store/modules/presentStore.js";
import createPersistedState from "vuex-persistedstate";

export default createStore({
	modules: {
		presentStore : presentStore,
	},
	plugins: [createPersistedState()],
});