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
import NotFound from "@/views/NotFoundPage.vue"

import Store from "@/store"
import {getUserDetailUseToken} from '@/api/user.js';
Vue.use(VueRouter);

const routes = [
    {
        path: "/",
        name: "home",
        component: Home,
        redirect: "/landing",
        children: [
            {
                path: "/eventRoom/:userid",
                name: "eventRoom",
                component: eventRoom,
                beforeEnter: getUserInfo,
            },
            {
                path: "/diary/:userid",
                name : "Diary",
                component: Diary,
                redirect: "/diary/:userid",
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
                        path: "edit/:boardId",
                        name: "BoardEdit",
                        component: BoardEdit,
                    },
                    {
                        path: "detail/:boardId",
                        name: "BoardDetail",
                        component: BoardDetail,
                    }
                ]
            },
            {
                path: "/present/:userid",
                name: "Present",
                component: PresentPage,
                redirect : "/present/:userid/manage",
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
    },
    {
        path: "*",
        name: "NotFound",
        component: NotFound,
    }
]
const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes,
});

function getUserInfo(to, from, next) {
    let userId = to.params.userid;
    console.log("Router-indx.js-getUserInfo-현재 페이지의 userId:",userId);
    Store.commit("userStore/setCurUserId",userId);
    const getCurUser = Store.getters['userStore/getCurUser'];
    console.log(getCurUser);


    let accessToken = localStorage.getItem("accessToken")
    if(accessToken == null){
        console.log("Permission : Guest")//2
        Store.commit("userStore/setCurUserPermission",2);
    }else{
        getUserDetailUseToken(accessToken,(res)=>{
            console.log("router-index.js-getUserInfo-getUserDetailUseToken 호출")
            console.log(res.data.User.id);
            let compareId = res.data.User.id;
            if(compareId != userId){
                console.log("Permission : Guest(Logined User)") //1
                Store.commit("userStore/setCurUserPermission",1);
            }else{
                console.log("Permission : User") //0
                Store.commit("userStore/setCurUserPermission",0);
            }
        });
    }
    
    next();
  }

export default router;