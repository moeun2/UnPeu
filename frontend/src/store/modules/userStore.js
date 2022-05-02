export const userStore={
    namespaced : true,
    state:{
        user: null,
        curUser:null,
    },
    getters:{
        getCurUser(state){
            console.log("getCurUser Getters - 호출", state.curUser)
            return state.curUser;
        }
    },
    mutations:{
        setUser(state, user){
            state.user=user;
            console.log(state.user);
        },
        setUSerNull(state){
            state.user=null;
            console.log(state.user);
        },
        setCurUser(state, userId) {
            state.curUser = userId;
            console.log("setCurUser mutation : ", state.curUser);
        },
    },
    actions:{
    }
}