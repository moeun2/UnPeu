import * as Instance from "./index.js";
const api = Instance.createInstance();
const tokenApi = Instance.tokenInstance();

// 유저 선물 등록
function register(present, success, fail){
    tokenApi.post(`/present`,present,{headers : {'Content-Type': 'multipart/form-data'}}).then(success).catch(fail);
}

// 유저 선물 리스트 찾기
function search(id, success, fail){
    api.get(`/present/${id}`).then(success).catch(fail);
}

// 메세지 전송
function sendMessage(message, success, fail){
    api.post(`/present/message`, JSON.stringify(message)).then(success).catch(fail);
}

export{
    register,
    search,
    sendMessage,
}