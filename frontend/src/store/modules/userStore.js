export const userStore={
    namespaced : true,
    state:{
        loginUser: null,

    },
    getters:{

    },
    mutations:{
        setUser(state, user){
            state.loginUser=user;
            console.log(state.loginUser);
        },
        setUSerNull(state){
            state.loginUser=null;
            console.log(state.loginUser);
        },
        
    },
    actions:{
    }
}