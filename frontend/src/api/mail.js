import { createInstance } from "./index.js";
const api = createInstance();

// 이메일 보내기
function sendMail(text, success, fail) {
  api.post(`api/mail`, text).then(success).catch(fail);
}

export { sendMail };
