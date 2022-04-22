import Vue from "vue";
import VueRouter from "vue-router";
import eventRoom from "@/components/EventRoom.vue"
import Home from "@/views/MainPage";

Vue.use(VueRouter);

const routes = [
    {
        path: "/",
        name: "home",
        component: Home,
        children: [
            {
                path: "/eventRoom",
        name: "eventRoom",
        component: eventRoom
            }
        ]
    },
]
const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes,
  });
  
  export default router;