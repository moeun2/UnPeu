import { createInstance} from "./index.js";

const api = createInstance();

function kakaoLogin(code, success, fail){
    api.post(`/auth/kakao?code=`+code)
    .then(success).catch(fail)
}

function googleLogin(code, success, fail){
    api.post(`/auth/google?code=`+code)
    .then(success).catch(fail)
}

function getUserDetail(userId, success, fail){
    api.get(`/users/`+userId)
    .then(success).catch(fail)
    console.log("getUserDetail 실행됨")
}

function getUserDetailUseToken(token,success, fail){
    api.get(`/users`,{
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem("accessToken")
        }
       })
    .then(success).catch(fail)
    console.log("getUserDetailUseToken 실행됨")
}

function updateUserImg(img,success,fail){

    api.patch(`/users/img`,img,{
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem("accessToken"),
          'Content-Type': 'multipart/form-data'
        }
       })
    .then(success).catch(fail)
    console.log("updateUserImg 실행됨")
}

function updateUserInfo(userInfo,success,fail){

    api.patch("/users/info",{
        userInfo: userInfo,}
        ,{
        headers: {
          Authorization: 'Bearer ' +localStorage.getItem("accessToken")
        }
       })
    .then(success).catch(fail)
    console.log("updateUserInfo 실행됨")
}

function updateUserTitle(userTitle,success,fail){
    api.patch(`/users/title?userTitle=`+userTitle,null,{
        headers: {
          Authorization: 'Bearer ' +localStorage.getItem("accessToken")
        }
       })
    .then(success).catch(fail)
    console.log("updateUserTitle 실행됨")
}

export{
    kakaoLogin,
    googleLogin,
    getUserDetail,
    updateUserImg,
    updateUserInfo,
    updateUserTitle,
    getUserDetailUseToken
}