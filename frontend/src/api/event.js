import { tokenInstance } from "./index.js";

const tokenApi = tokenInstance();

function getMessage(success, fail){
    tokenApi.get(`/api/message`)
    .then(success)
    .catch(fail)
    console.log("실행됨")
}

export{
    getMessage,
}
