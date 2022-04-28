<template>
    <div class="view">
      <div class="background">
        <main>
          <div class="holder hd1"></div>
          <div class="holder hd2"></div>
          <div class="holder hd3"></div>
          <div class="holder hd4"></div>
          <section class="profile-section">
            <div class="profile-dot">
              <div class="profile-paper">
                <div class="profile-wrap">
                  <div class="visitor-counter">
                    <p class="text-today">103</p>
                    <p class="text-total">13042</p>
                  </div>
                  <div class="profile">
                    <p class="text-today-is">
                      BGM IS .. <span> Y - 프리스타일</span>
                    </p>
                    <img
                      class="profile-img"
                      :src="this.userImg"
                      alt="profile"
                    />
                    <div class="desc-wrap">
                      <div class="text-desc" >
                        {{this.userInfo}}
                      </div>
                    </div>
                    <div class="info-wrap">
                      <a class="info-name" href="#">{{this.userName}}</a><br/>
                      <!-- <p class="text-email">ssafykim@ssafy.com</p> -->
                      <div style="display: flex">
                      <router-link :to="{ name: 'PresentManage' }">
                      <button class="item">
                        <img src="https://i.imgur.com/nupfePY.png" >
                        <p class="arrow_box">받고 싶은 선물 등록!</p>
                      </button>
                      </router-link>
                      <router-link :to="{ name: 'Login' }">
                      <button class="item">
                        <img src="https://i.imgur.com/Fqfvown.png" >
                        <p class="arrow_box">로그인</p>
                      </button>
                      </router-link>
                      <button class="item" @click="logout">
                        <img src="https://i.imgur.com/Fqfvown.png" >
                        <p class="arrow_box">로그아웃</p>
                      </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </section>
          <section class="main-section">
            <div class="main-dot">
              <div class="main-paper">
                <div class="main-wrap">
                  <div class="nav">
                    <ul>
                      <router-link :to="{ name: 'eventRoom',params: {userid:  $route.params.userid} }"><li :class="[activeCheckClass]" @click="checkHome()">홈</li></router-link>
                      <router-link :to="{ name: 'Diary',params: {userid: $route.params.userid} }"><li :class="[activeClass]" @click="checkDiary()">다이어리</li></router-link>
                    </ul>
                  </div>
                  <div class="title-wrap">
                    <p class="title"><a href="#">{{this.userTitle}}</a></p>
                  </div>
                  <div class="main">
                    <router-view />
                  </div>
                </div>
              </div>
            </div>
          </section>
        </main>
      </div>
    </div>
</template>

<script>
import {getUserDetailUseToken,getUserDetail} from '@/api/user.js';
export default {
  name: 'App',
  data() {
      return {
          activeCheckClass: 'menu-item mi-1 menu-checked',
          activeClass: 'menu-item mi-3',
          userName: "이름을 변경해주세요",
          userInfo: "선물주는 사람 차칸 사람",
          userTitle: "오늘은 어른이날, 선물사주라주",
          userImg: "https://i.imgur.com/nupfePY.png",
      }
  },

   created() {
    if(window.localStorage.getItem("accessToken")){
      //로그인 되어있는 상태 store inlogin true
      getUserDetailUseToken(window.localStorage.getItem("accessToken"),
      (res)=>{
        console.log(res.data.User);
        this.$store.commit("userStore/setUser",res.data.User)
      },
      ()=>{
        console.log("getUserDetailUseToken fail")
        window.localStorage.removeItem("accessToken")
        this.$router.go
      } )
    }
    //지금 접속한 페이지의 user 정보 가져오기
    getUserDetail(this.$route.params.userid,
      (res)=>{
        console.log(res.data.User);
        this.userName=res.data.User.userName
        if(res.data.User.userImg){
          this.userImg="http://localhost:8080"+res.data.User.userImg//baseurl으로 바꾸기
        }
        if(res.data.User.userInfo){
          this.userInfo= res.data.User.userInfo
        }
        if(res.data.User.userTitle){
          this.userInfo= res.data.User.userTitle
        }
        // this.$store.commit("userStore/setUser",res.data.User)
      },
      ()=>{
        console.log("getUserDetail fail")
      })


   },
  components: {
  },
  methods: {
      checkHome() {
          if(this.activeClass === 'menu-item mi-1 menu-checked') {
              this.activeClass = 'menu-item mi-3';
          }
          if(this.activeCheckClass === 'menu-item mi-3') {
              this.activeCheckClass = 'menu-item mi-1 menu-checked';
          }
      },
      checkDiary() {
          if(this.activeClass === 'menu-item mi-3') {
              this.activeClass = 'menu-item mi-1 menu-checked';
          }
          if(this.activeCheckClass === 'menu-item mi-1 menu-checked') {
              this.activeCheckClass = 'menu-item mi-3';
          }
      },

      logout(){
        //storage확인해서 도메인 확인
        if(confirm("로그아웃 하시겠습니까?")){
          window.localStorage.removeItem("accessToken")
          if(this.$store.state.userStore.user.socialDomain=="kakao"){
            window.location.replace(
              "https://kauth.kakao.com/oauth/logout?client_id=c0ad1801cdf80282754cf18e79556743&logout_redirect_uri=http://localhost:8081"
            );
          }
        }
    },
    }
};
</script>

<style>
@import url("@/assets/css/reset.css");
@import url("@/assets/css/style.css");

* {
  font-family: "hiffy" !important;
}
@font-face {
  font-family: "hiffy";
  src: url("@/assets/font/hiffy.ttf") format("truetype");
  font-weight: 400;
}

.view {
  background-image: url("https://i.imgur.com/EI9xcZH.png");
  background-size: cover;
  /* 수직 정렬 위해서 사용 */
  height: 100vh;
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.v-application--wrap {
    min-height: 100%;
}
.item {
  position: relative;
  display: inline-block;
}

.arrow_box {
  display: none;
  position: absolute;
  width: 100px;
  padding: 8px;
  left: 0;
  -webkit-border-radius: 8px;
  -moz-border-radius: 8px;  
  border-radius: 8px;
  background: #85b9eaef;
  color: #fff;
  font-weight: bold;
  z-index: 1;
}

img:hover + p.arrow_box {
  display: block;
}
</style>