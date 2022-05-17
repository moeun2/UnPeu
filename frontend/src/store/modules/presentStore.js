import { register, remove, search, sendMessage, update, peekMoney } from "@/api/present";

export const presentStore={
    namespaced : true,
    state:{
        presentList:[],
        appropriatePresent:null,
        money:0,
    },
    getters:{
        getNumberOfPresentList(state){
            if(state.presentList == null)
                return null
            else
                return state.presentList.Present.length;
        },
        GET_APPROPRIATE_VALUE_OF_PRESENT_LIST(state){
            console.log(state.presentList);
            if(state.presentList == null){
                return null;
            }
            else{
                let x=state.presentList.Present;
                let n=Number(state.money);
                state.appropriatePresent=null;
                let max=0;
                for (let index = 0; index < x.length; index++) {
                    if(Number(x[index].presentPrice)<n){
                            if(Number(x[index].presentPrice)>max){
                            state.appropriatePresent=x[index];
                            max=Number(x[index].presentPrice);
                        }
                    }
                }
            }
        }
    },
    mutations:{
        SET_PRESENT_LIST(state, presentList){
            state.presentList=presentList;
            //console.log(state.presentList);
        },
        REMOVE_PRESENT_FROM_ARRAY(state, presentId){
            const i=state.presentList.Present.map(item => item.presentId).indexOf(presentId);
            state.presentList.Present.splice(i, 1);
        },
        INSERT_PRESENT(state, present){
            state.presentList.Present.push(present);
        },
        UPDATE_PRESENT(state, present){
            const i=state.presentList.Present.map(item => item.presentId).indexOf(present.presentId);
            //console.log(state.presentList);
            state.presentList.Present.splice(i, 1);
            state.presentList.Present.splice(i, 0, present);
            //console.log(state.presentList);
        }
        ,
        RESET_PRESENT_LIST(state){
            state.presentList=null;
        },
        SET_MONEY(state,money){
            state.money=money;
        }
    },
    actions:{
         registerPresent({commit}, fd){
             register(
                fd,
                (response) => {
                    //console.log(response.data);
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
                    //console.log(response.data);
                    commit("SET_PRESENT_LIST", response.data);
                }
            )
        },
        sendPresentMessage({commit}, message){
            sendMessage(
                message,
                ()=>{
                    //console.log(response.data);
                    commit;
                }
            )
        },
        updatePresent({commit}, present){
            update(
                present,
                (response)=>{
                    //console.log(response.data);
                    commit("UPDATE_PRESENT",response.data.Present);
                }
            )
        },
        deletePresent({commit}, presentId){
            remove(
                presentId,
                ()=>{
                    //console.log(response.data);
                    commit("REMOVE_PRESENT_FROM_ARRAY",presentId);
                    return true;
                },
                ()=>{
                    return false;
                }
            )
        },
        getMoney({commit}, id){
            peekMoney(id,
                (response)=>{
                    console.log(response.data);
                    commit("SET_MONEY",response.data.Money);
                },
                ()=>{
                    return false;
                }
            )
        }
    }
}