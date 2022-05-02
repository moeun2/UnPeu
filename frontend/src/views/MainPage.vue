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
                    <p class="text-today" v-text="todayVisit"></p>
                    <p class="text-total" v-text="totalVisit"></p>
                  </div>
                  <div class="profile">
                    <p class="text-today-is">
                      BGM IS .. <span> Y - 프리스타일</span>
                    </p>
                    <!-- <v-icon class="img-update-icon" small @click="updateUserImg" v-if="isMyPage">mdi-image-edit-outline</v-icon> -->
                    <v-file-input class="img-update-icon"
                      v-if="isMyPage"
                      :rules="rules"
                      v-model="files"
                      hide-input
                      prepend-icon="mdi-camera"
                      accept="image/png, image/jpeg, image/bmp"
                    ></v-file-input>
                    <img
                      class="profile-img"
                      :src="this.userImg"
                      alt="profile 이미지가 없습니다"
                      onerror="this.src='https://i.imgur.com/nupfePY.png'"
                    />
                    <div class="desc-wrap">
                      <v-icon small class="title-update-icon" @click="updateUserInfo" v-if="isMyPage">mdi-pencil-outline</v-icon>
                      <div class="text-desc" v-html="userInfo"></div>
                    </div>
                    <div class="info-wrap">
                      <v-icon small @click="copyLink">mdi-link</v-icon>
                      <a class="info-name" href="#" > {{this.userName}}</a>
                      <div class="info-birth">{{this.userBirth}}</div>
                      <br/>
                      <p class="text-email">{{this.userEmail}}</p>
                      <div style="display: flex; margin-top: 10px;">
                      <router-link :to="{ name: 'PresentManage' }" v-if="isMyPage">
                      <button class="item">
                        <img src="https://i.imgur.com/nupfePY.png" >
                        <p class="arrow_box">받고 싶은 선물 등록!</p>
                      </button>
                      </router-link>
                      <router-link :to="{ name: 'Login' }" v-if="!isLogin">
                      <button class="item">
                        <img src="https://i.imgur.com/Fqfvown.png" >
                        <p class="arrow_box">로그인</p>
                      </button>
                      </router-link>
                      <router-link :to="{name: 'eventRoom',params: {userid:  $store.state.userStore.user.id}}" v-if="!isMyPage&&isLogin">
                        <!-- to="{name: 'eventRoom',params: {userid:  $store.state.userStore.user.id}}" -->
                      <button class="item">
                        <img src="https://i.imgur.com/Fqfvown.png" >
                        <p class="arrow_box">마이페이지</p>
                      </button>
                      </router-link>
                      <button class="item" @click="logout" v-if="isLogin">
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
                    
                    <p class="title"><v-icon plain retain-focus-on-click small @click="updateUserTitle" v-if="isMyPage">mdi-pencil-outline</v-icon><a href="#">{{this.userTitle}}</a></p>
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
import {getUserDetailUseToken,getUserDetail,updateUserImg,updateUserTitle,updateUserInfo} from '@/api/user.js';
import {FRONT_URL,API_BASE_URL} from '@/config/index';
// import store from '@/store';
export default {
  name: 'App',
  data() {
      return {
          activeCheckClass: 'menu-item mi-1 menu-checked',
          activeClass: 'menu-item mi-3',
          userName: "김싸피",
          userInfo: "선물주는 사람\n차칸 사람",
          userTitle: "오늘은 어른이날, 선물사주라주",
          userImg: "",
          userBirth: "1996.10.31",
          userEmail: "ssafykim@ssafy.com",
          isLogin: false,
          isMyPage: false,
          totalVisit:0,
          todayVisit:0,
          rules: [
            value => !value || value.size < 2000000 || 'Avatar size should be less than 2 MB!',
           ],
          files:null,
      }
  },
  watch: {
    files: function () {
      this.updateUserImg()
    },
    $route(to, form) {
     if (to.path !== form.path) this.changeParams(this.$route.params.userid)
   },

  },

   created() {
    if(window.localStorage.getItem("accessToken")){
      //로그인 되어있는 상태 store inlogin true
      getUserDetailUseToken(window.localStorage.getItem("accessToken"),
      (res)=>{
        console.log(res.data.User);
        this.$store.commit("userStore/setUser",res.data.User)
        this.isLogin=true;
        if(this.$route.params.userid==res.data.User.id){
          this.isMyPage=true
        }
      },
      ()=>{
        console.log("getUserDetailUseToken fail")
        this.isLogin=false;
        window.localStorage.removeItem("accessToken")
        this.$router.go
      } )
    }
    this.setUserData();

   },
  components: {
  },
  methods: {
    setUserData(){
      getUserDetail(this.$route.params.userid,
      (res)=>{
        console.log(res.data.User);
        this.userName=res.data.User.userName
        if(res.data.User.userImg){
          this.userImg=API_BASE_URL+res.data.User.userImg
        }
        if(res.data.User.userInfo){
          this.userInfo= res.data.User.userInfo
        }
        if(res.data.User.userTitle){
          this.userTitle= res.data.User.userTitle
        }
        if(res.data.User.userEmail){
          this.userEmail= res.data.User.userEmail
        }
        if(res.data.User.userBirth){
          this.userBirth= res.data.User.userBirth[0]+"."+res.data.User.userBirth[1]+"."+res.data.User.userBirth[2]
        }
        if(res.data.User.todayVisit){
          this.todayVisit= res.data.User.todayVisit
        }
        if(res.data.User.totalVisit){
          this.totalVisit= res.data.User.totalVisit
        }
      },
      ()=>{
        console.log("getUserDetail fail")
      })
    },

    changeParams(index) {
     if(window.localStorage.getItem("accessToken")){
      //로그인 되어있는 상태 store inlogin true
      getUserDetailUseToken(window.localStorage.getItem("accessToken"),
      (res)=>{
        console.log(res.data.User);
        this.$store.commit("userStore/setUser",res.data.User)
        this.isLogin=true;
        if(index==res.data.User.id){
          this.isMyPage=true
        }
      },
      ()=>{
        console.log("getUserDetailUseToken fail")
        this.isLogin=false;
        window.localStorage.removeItem("accessToken")
        this.$store.commit("userStore/setUserNull")
        this.$router.go
      } )
    }
    this.setUserData();
   },

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
        //storage확인해서 도메인 확인 //모달창 바꾸기
        if(confirm("로그아웃 하시겠습니까?")){
          window.localStorage.removeItem("accessToken")

          if(this.$store.state.userStore.user.socialDomain=="kakao"){
            this.$store.commit("userStore/setUSerNull")
            window.location.replace(
              "https://kauth.kakao.com/oauth/logout?client_id=c0ad1801cdf80282754cf18e79556743&logout_redirect_uri="+FRONT_URL
            );
          }
          this.$store.commit("userStore/setUSerNull")
          this.$router.push({name: "Landing"})
        }
      },

      async updateUserTitle(){
        const { value: title } = await this.$swal.fire({
          title: '타이틀을 입력해주세요!',
          input: 'text',
          inputLabel: '오른쪽 상단의 타이틀입니다. 귀여운 어필을 해보는건 어떨까요?',
          inputPlaceholder: '25자 이하로 작성해주세요.',
          inputAttributes: {
            maxlength: 25,
          },
          inputValidator:(value) => {
              if (!value) {
                return '타이틀을 한글자 이상 입력해주세요!'
              }
            }
        })

        if (title) {
          updateUserTitle(title,
          (res)=>{
            this.userTitle=res.data.User.userTitle
          },
        )
        }
      },

      async updateUserInfo(){
        const { value: info } = await this.$swal.fire({
          title: '소개글을 입력해주세요!',
          input: 'textarea',
          inputLabel: '프로필 사진 밑의 소개글입니다. 여러분을 소개해주세요 :)' ,
          inputPlaceholder: '50자 이하, 4줄 이하로 작성해주세요.',
          inputAttributes: {
            maxlength: 50,
          },
          inputValidator:(value) => {
              if (!value) {
                return '소개글을 한글자 이상 입력해주세요!'
              }else if(value.match(/[\n]/g) == null ? false : value.match(/[\n]/g).length+1>4){
                return '4줄 이하로 입력해주세요!'
              }
            }
        })

        if (info) {
          updateUserInfo(info.replace("\"", ""),
          (res)=>{
            this.userInfo=res.data.User.userInfo
            console.log(res.data.User.userInfo)
          })
        }
      },
    

      async updateUserImg(){
        let fd=new FormData();
        fd.append('file',this.files);

        updateUserImg(fd,
          (res)=>{
            this.userImg=API_BASE_URL+res.data.User.userImg
          },
        )
      },

      copyLink(){
        let currentUrl = window.document.location.href;

        let t = document.createElement("textarea");
        document.body.appendChild(t);
        t.value = currentUrl;
        t.select();
        document.execCommand('copy');
        document.body.removeChild(t);
        //복사완료 다이얼로그
        this.$swal.fire({
          icon: 'success',
          title: '링크가 저장되었습니다.',
          showConfirmButton: false,
          timer: 800
        })
    }

    },
    

};
</script>

<style scope>
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
.info-birth {
    /* content: "(♂) 1996.10.31"; */
    color: #a5a8aa;
    font-size: 10px;
    margin-left: 5px;
    font-weight: 400;
    display: inline;
  }

  .title-update-icon{
    position: absolute !important;
    left: 142px;
    bottom: 10px;
  }

  .img-update-icon{
    position: absolute !important;
    left: 197px;
    bottom: 309px;
  }

</style>