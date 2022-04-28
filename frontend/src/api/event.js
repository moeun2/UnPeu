import { tokenInstance } from "./index.js";

const tokenApi = tokenInstance();

function getMessage(success, fail){
    console.log(localStorage.getItem("accessToken")) // 여기서 null 뜨면 1. login 하고 들어갔는지 확인 / 2. application-localstorage에서 accessToken있는지 확인
    tokenApi.get(`/message`)
    .then(success)
    .catch(fail)
}

function saveMessage(messages, success, fail){
    api.post(`/api/message/messageToDiary`,messages)
    .then(success)
    .catch(fail)
    console.log("실행됨")
}

export{
    getMessage,
    saveMessage
}