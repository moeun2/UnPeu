import * as api from "@/api/user";
export const userStore = {
  namespaced: true,

  state: {
    user: null,
    currentUser: {
      id: "",
      permission: "",
      category: "",
      selectedDate: "",
    },
  },

  getters: {
    getCurUser(state) {
      // console.log("0:User/1:Guest(Logined User)/2:Guest")
      return state.currentUser;
    },
    getUser(state) {
      return state.user;
    },
  },

  mutations: {
    setUser(state, user) {
      state.user = user;
      // console.log(state.user);
    },

    setUSerNull(state) {
      state.user = null;
    },

    MU_CUR_USER_ID(state, userId) {
      state.currentUser.id = userId;
    },

    MU_CUR_USER_PERMISSION(state, permission) {
      state.currentUser.permission = permission;
    },
    
    MU_CUR_USER_CONCEPT(state, category, selectedDate) {
      state.currentUser.category = category;
      state.currentUser.selectedDate = selectedDate;
      console.log("MU_CUR_USER_CONCEPT : ", state.currentUser);
    },
  },

  actions: {
    /* 컨셉 등록 or 수정 */
    AC_UPDATE_CONCEPT({ commit }, data) {
      api.updateUserConcept(
        data,
        (response) => {
          console.log("AC_UPDATE_CONCEPT : ", response.data);
          commit(
            "MU_CUR_USER_CONCEPT",
            response.data.User.category,
            response.data.User.selectedDate
          );
          return response.data;
        },
        (err) => {
          console.log(err);
          return false;
        }
      );
    },
  },
};
