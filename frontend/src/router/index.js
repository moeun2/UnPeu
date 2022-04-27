import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/MainPage.vue"
import PresentPage from "@/views/PresentPage.vue";
import PresentPayment from "@/components/PresentPayment.vue";
import PresentManage from "@/components/PresentManage.vue";
import eventRoom from "@/components/EventRoom.vue"

import Diary from "@/views/DiaryPage.vue"
import BoardList from "@/components/diary/BoardList.vue"
import BoardWrite from "@/components/diary/BoardWrite.vue"
import BoardEdit from "@/components/diary/BoardEdit.vue";
import BoardDetail from "@/components/diary/BoardDetail.vue"

import Login from "@/views/LoginPage.vue"
import KakaoLogin from "@/components/login/KakaoLogin.vue"
import GoogleLogin from "@/components/login/GoogleLogin.vue"
import Landing from "@/views/LandingPage.vue"


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
        ]
    },
    {
        path: "/login",
        name: "Login",
        component: Login,
        children: [
            {
                path: "kakao",
                name: "KakaoLogin",
                component: KakaoLogin
            },
            {
                path: "google",
                name: "GoogleLogin",
                component: GoogleLogin
            },
        ]

    },
    {
        path: "/landing",
        name: "Landing",
        component: Landing,
    }
]
const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes,
});

export default router;