import { register, remove, search, sendMessage, update } from "@/api/present";

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
        REMOVE_PRESENT_FROM_ARRAY(state, presentId){
            console.log(state.presentList.Present)
            const i=state.presentList.Present.map(item => item.presentId).indexOf(presentId);
            console.log(i,presentId);
            state.presentList.Present.splice(i, 1);
        }
    },
    actions:{
         registerPresent({commit}, fd){
             register(
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
        },
        updatePresent({commit}, present){
            update(
                present,
                (response)=>{
                    console.log(response.data);
                    commit;
                }
            )
        },
        deletePresent({commit}, presentId){
            remove(
                presentId,
                (response)=>{
                    console.log(response.data);
                    commit("REMOVE_PRESENT_FROM_ARRAY",presentId);
                }
            )
        }
    }
}