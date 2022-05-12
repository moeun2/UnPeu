import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

import { presentStore } from "@/store/modules/presentStore.js";
import { userStore } from "@/store/modules/userStore.js";
import { diaryStore } from "@/store/modules/diaryStore.js";
import { eventStore } from "@/store/modules/eventStore.js";

export default new Vuex.Store({
  modules: {
    presentStore,
    userStore,
    diaryStore,
    eventStore
  },
  plugins: [
    createPersistedState({
      Storage: sessionStorage,
    }),
  ],
});
