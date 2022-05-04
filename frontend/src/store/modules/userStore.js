export const userStore = {
  namespaced: true,

  state: {
    user: null,
    currentUser: {
      id: "",
      permission: "",
    },
  },

  getters: {
    getCurUser(state) {
      // console.log("getCurUser Getters - 호출", state.currentUser);
      // console.log("0:User/1:Guest(Logined User)/2:Guest")
      return state.currentUser;
    },
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

    setCurUserPermission(state, permission) {
      state.currentUser.permission = permission;
      // console.log("parameter mutation : ", state.currentUser);
    },
  },
  
  actions: {},
};
