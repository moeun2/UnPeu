import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/MainPage.vue";
import PresentPage from "@/views/PresentPage.vue";
import PresentPayment from "@/components/present/payment/PresentPayment.vue";
import PresentManage from "@/components/present/management/PresentManage.vue";

import Event from "@/views/EventPage.vue";
import eventRoom from "@/components/eventRoom/EventRoom.vue";
import ConceptChange from "@/components/eventRoom/ConceptChange.vue";

import Diary from "@/views/DiaryPage.vue";
import BoardList from "@/components/diary/BoardList.vue";
import BoardWrite from "@/components/diary/BoardWrite.vue";
import BoardEdit from "@/components/diary/BoardEdit.vue";
import BoardDetail from "@/components/diary/BoardDetail.vue";

import Login from "@/views/LoginPage.vue";
import KakaoLogin from "@/components/login/KakaoLogin.vue";
import GoogleLogin from "@/components/login/GoogleLogin.vue";
import Landing from "@/views/LandingPage.vue";
import NotFound from "@/views/NotFoundPage.vue";

import Store from "@/store";
import { getUserDetailUseToken } from "@/api/user.js";
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
        name: "Event",
        component: Event,
        beforeEnter: getUserInfo,
        redirect: "/eventRoom/:userid",
        children: [
          {
            path: "/",
            name: "eventRoom",
            component: eventRoom,
          },
          {
            path: "conceptChange",
            name: "ConceptChange",
            component: ConceptChange,
          }
        ]
      },
      {
        path: "/diary/:userid",
        name: "Diary",
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
          },
        ],
      },
      {
        path: "/present",
        name: "Present",
        component: PresentPage,
        children: [
          {
            path: ":userid/manage",
            name: "PresentManage",
            component: PresentManage,
            beforeEnter: getUserPresentAndCheckGuest,
          },
          {
            path: ":userid/payment",
            name: "PresentPayment",
            beforeEnter: getUserPresent,
            component: PresentPayment,
          },
        ],
      },
    ],
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
    children: [
      {
        path: "kakao",
        name: "KakaoLogin",
        component: KakaoLogin,
      },
      {
        path: "google",
        name: "GoogleLogin",
        component: GoogleLogin,
      },
    ],
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
  },
];
const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

function getUserPresentAndCheckGuest(to, from, next){
  let loginCheck=Store.state.userStore.user;
  if(loginCheck==null) {
    alert("로그인을 하셔야 합니다!")
    router.push({
      name:"eventRoom",
      params: { userid: to.params.userid }
    })
  }
  else{
    let userId = to.params.userid;
    Store.dispatch("presentStore/searchList",userId);
    next();
  }
}

function getUserPresent(to, from, next){
  let userId = to.params.userid;
  Store.dispatch("presentStore/searchList",userId);
  // console.log(userId)
  next();
}

function getUserInfo(to, from, next) {
  let userId = to.params.userid;
  // console.log("Router-indx.js-getUserInfo-현재 페이지의 userId:", userId);
  Store.commit("userStore/setCurUserId", userId);
  // const getCurUser = Store.getters["userStore/getCurUser"];
  // console.log(getCurUser);

  let accessToken = localStorage.getItem("accessToken");
  if (accessToken == null) {
    // console.log("Permission : Guest"); //2
    Store.commit("userStore/setCurUserPermission", 2);
  } else {
    getUserDetailUseToken(accessToken, (res) => {
      // console.log("router-index.js-getUserInfo-getUserDetailUseToken 호출");
      // console.log(res.data.User.id);
      let compareId = res.data.User.id;
      if (compareId != userId) {
        // console.log("Permission : Guest(Logined User)"); //1
        Store.commit("userStore/setCurUserPermission", 1);
      } else {
        // console.log("Permission : User"); //0
        Store.commit("userStore/setCurUserPermission", 0);
      }
    });
  }
  
  next();
}

export default router;
