import { createInstance } from "./index.js";

const api = createInstance();

function registPresent(present, success, fail){
    api.post(`/present`).then(success).catch(fail);
}

export{
    registPresent,
}