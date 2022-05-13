import * as api from "@/api/user";
export const userStore = {
  namespaced: true,

  state: {
    user: null,
    currentUser: {
      id: "",
      permission: "",
      category:"",
      selectedDate :"",
    },
  },

  getters: {
    getCurUser(state) {
      // console.log("getCurUser Getters - 호출", state.currentUser);
      // console.log("0:User/1:Guest(Logined User)/2:Guest")
      return state.currentUser;
    },
    getUser(state){
      return state.user;
    }
  },

  mutations: {
    setUser(state, user) {
      state.user = user;
      // console.log(state.user);
    },

    setUSerNull(state) {
      state.user = null;
      // console.log(state.user);
    },

    setCurUserId(state, userId) {
      state.currentUser.id = userId;
      // console.log("setCurUserId mutation : ", state.currentUser);
    },
    setCurUserCategory(state, category) {
      state.currentUser.category = category;
      // console.log("setCurUserId mutation : ", state.currentUser);
    },
    setCurUserSelectedDate(state, selectedDate) {
      state.currentUser.selectedDate = selectedDate;
      // console.log("setCurUserId mutation : ", state.currentUser);
    },
    setCurUserPermission(state, permission) {
      state.currentUser.permission = permission;
      // console.log("parameter mutation : ", state.currentUser);
    },
  },
  
  actions: {

    /* 컨셉 등록 or 수정 */
    AC_UPDATE_CONCEPT({ commit }, data) {
      api.updateUserConcept(
        data,
        (response) => {
          console.log("AC_UPDATE_CONCEPT : ",response.data)
          commit("setCurUserCategory", response.data.category);
          commit("setCurUserSelectedDate", response.data.selectedDate);
          return response.data;
        },
        (err)=>{
          console.log(err);
          return false;
      }
      );
    },
  },
};
