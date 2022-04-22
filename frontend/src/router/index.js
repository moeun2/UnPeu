import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/mainPage.vue"
import PresentPage from "@/views/PresentPage.vue";
import PresentPayment from "@/components/PresentPayment.vue";
import PresentManage from "@/components/PresentManage.vue";
Vue.use(VueRouter);

const routes = [
    {
        path: "/",
        name: "home",
        component: Home
    },
    {
        path: "/present",
        name: "Present",
        component: PresentPage,
        redirect : "/present/manage",
        children:[
            {
                path: "manage",
                name: "PresentManage",
                component: PresentManage,
            },
            {
                path: "payment",
                name: "PresentPayment",
                component : PresentPayment,
            }
        ]
    }
]
const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes,
  });
  
  export default router;