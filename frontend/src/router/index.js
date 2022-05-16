import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/MainPage.vue";
import PresentPage from "@/views/PresentPage.vue";
import PresentPayment from "@/components/present/payment/PresentPayment.vue";
import PresentManage from "@/components/present/management/PresentManage.vue";
import eventRoom from "@/components/eventRoom/EventRoom.vue";

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
import { getUserDetail, getUserDetailUseToken } from "@/api/user.js";
import dayjs from "dayjs";
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
    beforeEnter: setDefaultEmail,
  },
  {
    path: "/landing",
    name: "Landing",
    component: Landing,
    beforeEnter: setDefaultEmail,
  },
  {
    path: "*",
    name: "NotFound",
    component: NotFound,
    beforeEnter: setDefaultEmail,
  },
];
const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});
function setDefaultEmail(to, from, next) {
  document.documentElement.setAttribute("color-theme", "default-email");
  next();
}
function getUserPresentAndCheckGuest(to, from, next) {
  let loginCheck = Store.state.userStore.user;
  if (loginCheck == null) {
    alert("로그인을 하셔야 합니다!");
    router.push({
      name: "eventRoom",
      params: { userid: to.params.userid },
    });
  } else {
    let userId = to.params.userid;
    Store.dispatch("presentStore/searchList", userId);
    next();
  }
}

function getUserPresent(to, from, next) {
  let userId = to.params.userid;
  Store.dispatch("presentStore/searchList", userId);
  // console.log(userId)
  next();
}

function getUserInfo(to, from, next) {

  let userId = to.params.userid;
  let accessToken = localStorage.getItem("accessToken");
  Store.commit("userStore/MU_CUR_USER_ID", userId);
  console.log("getUserInfo - CuruserId :", userId);

  

  getUserDetail(
    userId,
    (res) => {
      let today = null
      if(res.data.User.selectedDate != null){
        today = dayjs(res.data.User.selectedDate.join("-")).format(
          "YYYY-MM-DD"
        );
      }
      
      const data = {
        category: res.data.User.category,
        selectedDate: today,
      };
      /** Category, selectedDate 설정 **/
      Store.commit("userStore/MU_CUR_USER_CONCEPT", data);

      if (accessToken == null) {
        /** Permission 설정  **/
        Store.commit("userStore/MU_CUR_USER_PERMISSION", 2);
        next();
      } else {
        getUserDetailUseToken(accessToken, (res) => {
          let compareId = res.data.User.id;
          /** Permission 설정  **/
          if (compareId != userId) {
            Store.commit("userStore/MU_CUR_USER_PERMISSION", 1);
          } else {
            Store.commit("userStore/MU_CUR_USER_PERMISSION", 0);
          }
          next();
        });
      }
    },
    (err) => {
      console.log(err)
      // 없는 유저의 url로 들어갔을 때 에러처리
      if (err.response.status == 400) next("/error");
      // console.log(err)
    }
  );
}

export default router;
