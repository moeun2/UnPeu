import { createInstance, tokenInstance } from "./index.js";

const api = createInstance();
const tokenApi = tokenInstance();

function getMessage(userId, success, fail){
    api.get(`/message/list/${userId}`)
    .then(success)
    .catch(fail)
}

function saveMessage(messages, success, fail){
    tokenApi.post(`/message/messageToDiary`,messages)
    .then(success)
    .catch(fail)
    console.log("실행됨")
}

function resetMessage(success, fail){
    tokenApi.delete(`/message`)
    .then(success)
    .catch(fail)
    console.log("실행됨")
}

export{
    getMessage,
    saveMessage,
    resetMessage
}