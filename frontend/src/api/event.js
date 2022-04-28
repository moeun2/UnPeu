import { createInstance } from "./index.js";

const api = createInstance();

function getMessage(success, fail){
    api.get(`/api/message`)
    .then(success)
    .catch(fail)
    console.log("실행됨")
}

export{
    getMessage,
}
