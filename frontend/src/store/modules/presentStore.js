import { register, remove, search, sendMessage, update } from "@/api/present";

export const presentStore={
    namespaced : true,
    state:{
        presentList:[]
    },
    getters:{
        
    },
    mutations:{
        SET_PRESENT_LIST(state, presentList){
            state.presentList=presentList;
            console.log(state.presentList);
        },
        REMOVE_PRESENT_FROM_ARRAY(state, presentId){
            const i=state.presentList.Present.map(item => item.presentId).indexOf(presentId);
            state.presentList.Present.splice(i, 1);
        },
        INSERT_PRESENT(state, present){
            state.presentList.Present.push(present);
        },
        RESET_PRESENT_LIST(state){
            state.presentList=null;
        }
    },
    actions:{
         registerPresent({commit}, fd){
             register(
                fd,
                (response) => {
                    console.log(response.data);
                    commit("INSERT_PRESENT", response.data.Present);
                    return response.data.Present;
                },
                ()=>{
                    return false;
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
                    return true;
                },
                ()=>{
                    return false;
                }
            )
        }
    }
}