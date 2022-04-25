import { createInstance } from "./index.js";

const api = createInstance();

function loginUser(code, success, fail){
    api.post(`/api/auth/kakao?code=`+code)
    .then(success).catch(fail)
    console.log("실행됨")
}

export{
    loginUser,
}