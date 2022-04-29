export const userStore={
    namespaced : true,
    state:{
        user: null,

    },
    getters:{

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
    },
    actions:{
    }
}