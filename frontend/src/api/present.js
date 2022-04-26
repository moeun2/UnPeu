import { createInstance } from "./index.js";
const api = createInstance();

// 유저 선물 등록
function register(present, success, fail){
    api.post(`api/present`,present,{headers : {'Content-Type': 'multipart/form-data'}}).then(success).catch(fail);
}

// 유저 선물 리스트 찾기
function search(id, success, fail){
    api.get(`api/present/${id}`).then(success).catch(fail);
}

// 메세지 전송
function sendMessage(message, success, fail){
    api.post(`api/present/message`, JSON.stringify(message)).then(success).catch(fail);
}

export{
    register,
    search,
    sendMessage,
}