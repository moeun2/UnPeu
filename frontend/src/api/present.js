import { createInstance } from "./index.js";
const api = createInstance();

// 유저 선물 등록
function register(present, success, fail){
    api.post(`api/present`,present,{headers : {'Content-Type': 'multipart/form-data'}}).then(success).catch(fail);
}


// 선물 수정
function update(present, success, fail){
    api.put(`api/present/${present.presentId}`,present.fd,{headers : {'Content-Type': 'multipart/form-data'}}).then(success).catch(fail);
}


// 선물 삭제
function remove(presentId, success, fail){
    api.delete(`api/present/${presentId}`).then(success).catch(fail);
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
    update,
    remove,
    search,
    sendMessage,
}