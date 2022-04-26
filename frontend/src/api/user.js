import { createInstance } from "./index.js";

const api = createInstance();

function kakaoLogin(code, success, fail){
    api.post(`/api/auth/kakao?code=`+code)
    .then(success).catch(fail)
    console.log("실행됨")
}

function googleLogin(code, success, fail){
    api.post(`/api/auth/google?code=`+code)
    .then(success).catch(fail)
    console.log("실행됨")
}

export{
    kakaoLogin,
    googleLogin,
}