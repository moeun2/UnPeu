import { createInstance } from "./index.js";

const api = createInstance();

function getMessage(userId, success, fail) {
  api.get(`/message/list/${userId}`).then(success).catch(fail);
}

function saveMessage(messages, success, fail) {
  api
    .post(`/message/messageToDiary`, messages, {
      headers: {
        Authorization: "Bearer " + localStorage.getItem("accessToken"),
        "Content-Type": "application/json",
      },
    })
    .then(success)
    .catch(fail);
  // console.log("실행됨");
}

function resetMessage(success, fail) {
  api
    .delete(`/message`, {
      headers: {
        Authorization: "Bearer " + localStorage.getItem("accessToken"),
        "Content-Type": "application/json",
      },
    })
    .then(success)
    .catch(fail);
  // console.log("실행됨");
}

export { getMessage, saveMessage, resetMessage };
