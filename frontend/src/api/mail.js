import { createInstance } from "./index.js";
const api = createInstance();

// 이메일 보내기
function sendMail(text, success, fail) {
  // console.log(text);
  api.post(`/mail`, JSON.stringify(text)).then(success).catch(fail);
}

export { sendMail };
