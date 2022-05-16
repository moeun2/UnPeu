import * as Instance from "./index.js";
const api = Instance.createInstance();

// 유저 선물 등록
function register(present, success, fail) {
  const accessToken = localStorage.getItem("accessToken");
  api
    .post(`/present`, present, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "multipart/form-data",
      },
    })
    .then(success)
    .catch(fail);
}

// 선물 수정
function update(present, success, fail) {
  api
    .put(`/present/${present.presentId}`, present.fd, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then(success)
    .catch(fail);
}

// 선물 삭제
function remove(presentId, success, fail) {
  api.delete(`/present/${presentId}`).then(success).catch(fail);
}

// 유저 선물 리스트 찾기
function search(id, success, fail) {
  api.get(`/present/${id}`).then(success).catch(fail);
}

// 메세지 전송
function sendMessage(message, success, fail) {
  api
    .post(`/present/message`, JSON.stringify(message))
    .then(success)
    .catch(fail);
}

function peekMoney(success,fail){
  api
    .get(`/present/message/money`)
    .then(success)
    .catch(fail);
}

export { register, update, remove, search, sendMessage, peekMoney };
