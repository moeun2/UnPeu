import { regist, search, sendMessage } from "@/api/present";

export const presentStore={
    namespaced : true,
    state:{
        presentList:[],
    },
    getters:{

    },
    mutations:{
        SET_PRESENT_LIST(state, presentList){
            state.presentList=presentList;
            console.log(state.presentList);
        },
        
    },
    actions:{
         registPresent({commit}, fd){
             regist(
                fd,
                (response) => {
                    if(response.data.message === "success") {
                        console.log("성공");
                        commit;
                    }
                }
            )
        },
        searchList({commit}, id){
            search(
                id,
                (response)=>{
                    console.log(response.data);
                    commit("SET_PRESENT_LIST", response.data);
                }
            )
        },
        sendPresentMessage({commit}, message){
            sendMessage(
                message,
                (response)=>{
                    console.log(response.data);
                    commit;
                }
            )
        }
    }
}