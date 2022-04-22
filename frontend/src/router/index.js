import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/mainPage.vue"
import PresentPage from "@/views/PresentPage.vue";
import PresentPayment from "@/components/PresentPayment.vue";
import PresentManage from "@/components/PresentManage.vue";

import Diary from "@/views/DiaryPage.vue"
import BoardList from "@/components/diary/BoardList.vue"
import BoardWrite from "@/components/diary/BoardWrite.vue"
import BoardEdit from "@/components/diary/BoardEdit.vue";
import BoardDetail from "@/components/diary/BoardDetail.vue"


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
    },
    {
        path: "/diary",
        name : "Diary",
        component: Diary,
        redirect: "/diary",
        children: [
            {
                path: "/",
                name: "BoardList",
                component: BoardList,
            },
            {
                path: "write",
                name: "BoardWrite",
                component: BoardWrite,
            },
            {
                path: "edit/:diaryId",
                name: "BoardEdit",
                component: BoardEdit,
            },
            {
                path: "detail/:diaryId",
                name: "BoardDetail",
                component: BoardDetail,
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