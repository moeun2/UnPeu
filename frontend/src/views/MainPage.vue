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
                  <v-file-input
                    class="img-update-icon"
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
                    <v-icon
                      small
                      class="title-update-icon"
                      @click="updateUserInfo"
                      v-if="isMyPage"
                      >mdi-pencil-outline</v-icon
                    >
                    <div class="text-desc" v-html="userInfo"></div>
                  </div>
                  <div class="info-wrap">
                    <div class="speech-bubble">
                      친구에게<br />공유해보세요~!
                    </div>

                    <v-icon small @click="copyLink">mdi-link</v-icon>
                    <a class="info-name" href="#"> {{ this.userName }}</a>
                    <div class="info-birth">{{ this.userBirth }}</div>
                    <br />
                    <p class="text-email">{{ this.userEmail }}</p>
                    <div style="display: flex; margin-top: 10px">
                      <router-link
                        :to="{ name: 'PresentManage' }"
                        v-if="isMyPage"
                      >
                        <button class="item">
                          <v-icon>mdi-gift-open</v-icon>
                          <!-- <img src="https://i.imgur.com/nupfePY.png" /> -->
                          <p class="arrow_box">받고 싶은 선물 등록!</p>
                        </button>
                      </router-link>

                      <router-link :to="{ name: 'Login' }" v-if="!isLogin">
                        <button class="item">
                          <v-icon class="v-icon">mdi-login</v-icon>
                          <!-- <img src="https://i.imgur.com/Fqfvown.png" /> -->
                          <p class="arrow_box">로그인</p>
                        </button>
                      </router-link>
                      <router-link
                        :to="{
                          name: 'eventRoom',
                          params: { userid: $store.state.userStore.user.id },
                        }"
                        v-if="!isMyPage && isLogin"
                      >
                        <button class="item">
                          <v-icon>mdi-home</v-icon>
                          <!-- <img src="https://i.imgur.com/Fqfvown.png" /> -->
                          <p class="arrow_box">마이페이지</p>
                        </button>
                      </router-link>
                      <button
                        class="item"
                        @click="logout"
                        v-if="isLogin"
                        style="margin-left: 5px"
                      >
                        <v-icon>mdi-logout</v-icon>
                        <!-- <img src="https://i.imgur.com/Fqfvown.png" /> -->
                        <p class="arrow_box">로그아웃</p>
                      </button>
                      <button
                        class="item"
                        @click="userSetting"
                        v-if="isMyPage && isLogin"
                        style="margin-left: 5px"
                      >
                        <v-icon>mdi-account-cog</v-icon>
                        <!-- <img src="https://i.imgur.com/Fqfvown.png" /> -->
                        <p class="arrow_box">회원정보</p>
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>
        <section class="main-section">
          <div class="speech-bubble-div">
            <div class="speech-bubble2">
              <a :href="this.url" style="color: white">
                더 상세한 사용방법과<br />이벤트를 알고싶다면?<br />(👉Click
                Here!👈)</a
              >
            </div>
            <!-- <img class="speech-bubble2-img" src="@/assets/main_logo4.gif" /> -->
          </div>
          <div class="main-dot">
            <div class="main-paper">
              <div class="main-wrap">
                <div class="nav">
                  <ul>
                    <router-link
                      :to="{
                        name: 'eventRoom',
                        params: { userid: $route.params.userid },
                      }"
                      ><li :class="[activeCheckClass]" @click="checkHome()">
                        홈
                      </li></router-link>
                    <router-link
                      :to="{
                        name: 'Diary',
                        params: { userid: $route.params.userid },
                      }"
                      ><li :class="[activeClass]" @click="checkDiary()">
                        다이어리
                      </li></router-link
                    >
                  </ul>
                </div>
                <div class="title-wrap">
                  <p class="title">
                    <v-icon
                      plain
                      retain-focus-on-click
                      small
                      @click="updateUserTitle"
                      v-if="isMyPage"
                      >mdi-pencil-outline</v-icon
                    ><a @click="goToMainPage()">{{ this.userTitle }}</a>
                  </p>
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
    <LinkShareModal
      v-if="showModal"
      @close="showModal = false"
    ></LinkShareModal>
  </div>
</template>
<script>
import {
  getUserDetailUseToken,
  getUserDetail,
  updateUserImg,
  updateUserTitle,
  updateUserInfo,
  increaseVisit,
  updateUserEmailBirth,
  deleteUser,
} from "@/api/user.js";
import { EVENT_URL, FRONT_URL, API_BASE_URL } from "@/config/index";
import LinkShareModal from "@/components/option/LinkShareModal.vue";
import { mapMutations } from "vuex";
import flatpickr from "flatpickr";
import "flatpickr/dist/flatpickr.min.css";

const presentStore = "presentStore";
// import store from '@/store';
export default {
  name: "App",
  data() {
    return {
      url: EVENT_URL,
      activeCheckClass: "menu-item mi-1 menu-checked",
      activeClass: "menu-item mi-3",
      userName: "김싸피",
      userInfo: "선물주는 사람\n차칸 사람",
      userTitle: "오늘은 어른이날, 선물사주라주",
      userImg: "",
      userBirth: "1996.10.31",
      userEmail: "ssafykim@ssafy.com",
      isLogin: false,
      isMyPage: false,
      totalVisit: 0,
      todayVisit: 0,
      showModal: false,
      rules: [
        (value) =>
          !value ||
          value.size < 2000000 ||
          "Avatar size should be less than 2 MB!",
      ],
      files: null,
    };
  },
  watch: {
    files: function () {
      this.updateUserImg();
    },
    $route(to, form) {
      if (to.path !== form.path) this.changeParams(this.$route.params.userid);
    },
  },

  created() {
    this.test();
    if (window.localStorage.getItem("accessToken")) {
      //로그인 되어있는 상태 store inlogin true
      getUserDetailUseToken(
        window.localStorage.getItem("accessToken"),
        (res) => {
          // console.log(res.data.User);
          this.$store.commit("userStore/setUser", res.data.User);
          this.isLogin = true;
          if (this.$route.params.userid == res.data.User.id) {
            this.isMyPage = true;
          }
        },
        () => {
          // console.log("getUserDetailUseToken fail");
          this.isLogin = false;
          window.localStorage.removeItem("accessToken");
          this.$router.go;
        }
      );
    }
    this.setUserData();

    //조회수 증가
    if (
      window.location.href ==
      FRONT_URL + "/eventRoom/" + this.$route.params.userid) 
      {
        increaseVisit(
          this.$route.params.userid,
          (res) => {
            if (res.data.User.todayVisit) {
              this.todayVisit = res.data.User.todayVisit;
            }
            if (res.data.User.totalVisit) {
              this.totalVisit = res.data.User.totalVisit;
            }
          },
        );
        
    }

  },
  components: {
    LinkShareModal,
  },

  methods: {
    test() {
      const isUserColorTheme = localStorage.getItem("color-theme");
      // const isOsColorTheme = window.matchMedia("(prefers-color-scheme: dark)")
      //   .matches
      //   ? "dark"
      //   : "light";
      // console.log(isUserColorTheme,isOsColorTheme)
      // const getUserTheme = () =>
      //   isUserColorTheme ? isUserColorTheme : isOsColorTheme;
      let getUserTheme='';
      console.log("isUserColorTheme : ",isUserColorTheme)
      if(isUserColorTheme){
        getUserTheme = isUserColorTheme;
      }else{
        getUserTheme = "light"
      }

      if (getUserTheme === "dark") {
        localStorage.setItem("color-theme", "dark");
        document.documentElement.setAttribute("color-theme", "dark");
        // $checkbox.setAttribute('checked', true);
      } else {
        localStorage.setItem("color-theme", "light");
        document.documentElement.setAttribute("color-theme", "light");
      }
    },
    ...mapMutations(presentStore, ["RESET_PRESENT_LIST"]),
    goToMainPage() {
      if (localStorage.getItem("color-theme") === "light") {
        localStorage.setItem("color-theme", "dark");
        document.documentElement.setAttribute("color-theme", "dark");
        // $checkbox.setAttribute('checked', true);
      } else {
        localStorage.setItem("color-theme", "light");
        document.documentElement.setAttribute("color-theme", "light");
      }
      // this.$router.push({ name: "eventRoom" }).catch(()=>{});
    },
    setUserData() {
      getUserDetail(
        this.$route.params.userid,
        (res) => {
          // console.log(res.data.User);
          this.userName = res.data.User.userName;
          if (res.data.User.userImg) {
            this.userImg = API_BASE_URL + res.data.User.userImg;
          } else {
            this.userImg = "";
          }

          if (res.data.User.userInfo) {
            this.userInfo = res.data.User.userInfo;
          } else {
            this.userInfo = "선물주는 사람\n차칸 사람";
          }

          if (res.data.User.userTitle) {
            this.userTitle = res.data.User.userTitle;
          } else {
            this.userTitle = "오늘은 어른이날, 선물사주라주";
          }
          if (res.data.User.userEmail) {
            this.userEmail = res.data.User.userEmail;
          }
          if (res.data.User.userBirth) {
            this.userBirth =
              res.data.User.userBirth[0] +
              "." +
              res.data.User.userBirth[1] +
              "." +
              res.data.User.userBirth[2];
          }
          if (res.data.User.todayVisit) {
            this.todayVisit = res.data.User.todayVisit;
          }
          if (res.data.User.totalVisit) {
            this.totalVisit = res.data.User.totalVisit;
          }
        },
        () => {
          this.$router.push({ name: "NotFound" });
        }
      );
    },

    changeParams(index) {
      if (window.localStorage.getItem("accessToken")) {
        //로그인 되어있는 상태 store inlogin true
        getUserDetailUseToken(
          window.localStorage.getItem("accessToken"),
          (res) => {
            // console.log(res.data.User);

            this.$store.commit("userStore/setUser", res.data.User);
            this.isLogin = true;
            if (index == res.data.User.id) {
              this.isMyPage = true;
            }
          },
          () => {
            // console.log("getUserDetailUseToken fail")

            this.isLogin = false;
            window.localStorage.removeItem("accessToken");
            this.$store.commit("userStore/setUserNull");
            this.$router.go;
          }
        );
      }
      this.setUserData();
    },

    checkHome() {
      if (this.activeClass === "menu-item mi-1 menu-checked") {
        this.activeClass = "menu-item mi-3";
      }
      if (this.activeCheckClass === "menu-item mi-3") {
        this.activeCheckClass = "menu-item mi-1 menu-checked";
      }
    },

    checkDiary() {
      if (this.activeClass === "menu-item mi-3") {
        this.activeClass = "menu-item mi-1 menu-checked";
      }
      if (this.activeCheckClass === "menu-item mi-1 menu-checked") {
        this.activeCheckClass = "menu-item mi-3";
      }
    },

    logout() {
      //storage확인해서 도메인 확인 //모달창 바꾸기
      this.$swal
        .fire({
          icon: "question",
          title: "Logout",
          html: "앙뿌에서 로그아웃 하시겠습니까? ",
          showCancelButton: true,
        })
        .then((result) => {
          /* Read more about isConfirmed, isDenied below */
          if (result.isConfirmed) {
            window.localStorage.removeItem("accessToken");
            this.RESET_PRESENT_LIST();
            if (this.$store.state.userStore.user.socialDomain == "kakao") {
              this.$store.commit("userStore/setUSerNull");
              window.location.replace(
                "https://kauth.kakao.com/oauth/logout?client_id=c0ad1801cdf80282754cf18e79556743&logout_redirect_uri=" +
                  FRONT_URL
              );
            }
            this.$store.commit("userStore/setUSerNull");
            this.$router.push({ name: "Landing" });
          }
        });
    },

    async updateUserTitle() {
      const { value: title } = await this.$swal.fire({
        title: "타이틀을 입력해주세요!",
        input: "text",
        inputValue: this.userTitle,
        inputLabel:
          "오른쪽 상단의 타이틀입니다. 귀여운 어필을 해보는건 어떨까요?",
        inputPlaceholder: "25자 이하로 작성해주세요.",
        inputAttributes: {
          maxlength: 25,
          rows: 4,
          'spellcheck':'false'

        },

        inputValidator: (value) => {
          if (!value) {
            return "타이틀을 한글자 이상 입력해주세요!";
          }
        },
      });

      if (title) {
        updateUserTitle(title, (res) => {
          this.userTitle = res.data.User.userTitle;
        });
      }
    },

    async updateUserInfo() {
      const { value: info } = await this.$swal.fire({
        title: "소개글을 입력해주세요!",
        // html:"<textarea placeholder />",
        input: "textarea",
        inputValue: this.userInfo,
        inputLabel: "프로필 사진 밑의 소개글입니다. 여러분을 소개해주세요 :)",
        inputPlaceholder: "50자 이하, 4줄 이하로 작성해주세요.",
        inputAttributes: {
          maxlength: 50,
          rows:4,
          'spellcheck':'false'

        },
        inputValidator: (value) => {
          if (!value) {
            return "소개글을 한글자 이상 입력해주세요!";
          } else if (
            value.match(/[\n]/g) == null
              ? false
              : value.match(/[\n]/g).length + 1 > 4
          ) {
            return "4줄 이하로 입력해주세요!";
          }
        },
      });
      if (info) {
        updateUserInfo(info.replace('"', ""), (res) => {
          this.userInfo = res.data.User.userInfo;
          // console.log(res.data.User.userInfo)
        });
      }
    },

    async updateUserImg() {
      let fd = new FormData();
      fd.append("file", this.files);

      updateUserImg(fd, (res) => {
        this.userImg = API_BASE_URL + res.data.User.userImg;
      });
    },

    copyLink() {
      this.showModal = true;
    },

    async userSetting(){
      let flatpickrInstance;

      await this.$swal.fire({
        title: "회원 정보",
        icon: 'info',
        html:
          '<div>이메일:<input input type="email" placeholder="이메일을 입력해주세요" id="swal-input1" class="swal2-input" value='+this.userEmail+'></div>' +
          '<div>생 일 :<input placeholder="생일을 입력해주세요" class="swal2-input" id="expiry-date"value='+this.userBirth+'></div>',
        inputLabel:
          "여러분의 이메일과 생일을 입력해주세요. 드디어 마이페이지가 생성됩니다 :)",
        stopKeydownPropagation: false,
        focusConfirm: true,
        showDenyButton: true,
        showCancelButton: true,
        cancelButtonText:'취소',
        confirmButtonText: '수정',
        denyButtonText: `회원 탈퇴`,
        preConfirm: () => {
          var exptext = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
          if (!flatpickrInstance.selectedDates[0]) {
            this.$swal.showValidationMessage(`생일을 입력해주세요`);
          }else if (flatpickrInstance.selectedDates[0] > new Date()) {
            this.$swal.showValidationMessage(`혹시.. 아직 안태어나셨나요? 생일을 올바르게 입력해주세요 :)`)
          }
          if (!document.getElementById("swal-input1").value||!exptext.test(document.getElementById("swal-input1").value)) {
            this.$swal.showValidationMessage(`이메일을 입력해주세요`);
          }
        },
        willOpen: () => {
          flatpickrInstance = flatpickr(
            this.$swal.getPopup().querySelector("#expiry-date"),
            {
              allowInput:true
            }
          );
        },
      }).then((result) => {
        if (result.isConfirmed) {
          const data={
            userEmail: document.getElementById("swal-input1").value,
            userBirth: document.getElementById("expiry-date").value
          }
            updateUserEmailBirth(localStorage.getItem("accessToken"), data, (res) => {
            console.log("success change email and birth")
            this.$swal.fire('수정을 성공했습니다!', '', 'success')
            this.userEmail= res.data.User.userEmail
            this.userBirth =
              res.data.User.userBirth[0] +
              "." +
              res.data.User.userBirth[1] +
              "." +
              res.data.User.userBirth[2];
          });
        } else if (result.isDenied) {
          this.$swal.fire({
              title: '정말 회원을 탈퇴하시겠습니까?',
              showCancelButton: true,
            }).then((result) => {
              if (result.isConfirmed) {
                deleteUser(() => {
                  this.$swal.fire('회원 탈퇴되었습니다.', '', 'success')
                  localStorage.removeItem("accessToken")
                  this.$store.commit("userStore/setUSerNull")
                  this.$router.push({name:"Landing"})
                })
              }
            })
        }
      })
    },

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

:root[color-theme="default"] {
  --background: var(--test2);
  --profile : var(--pink-color);

}
:root[color-theme="birthday"] {
  --background: var(--test2);
  --profile : var(--pink-color);

}
:root[color-theme="light"] {
  --background: var(--test2);
  --profile : var(--pink-color);

}

:root[color-theme="dark"] {
  --background: var(--test);
  --profile : var(--yellow-color);
}

.view {
  background-image: var(--background);
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
  width: 120px;
  padding: 8px;
  left: 0;
  -webkit-border-radius: 8px;
  -moz-border-radius: 8px;
  border-radius: 8px;
  background: var(--speech-bubble-color);
  color: #fff;
  font-weight: bold;
  z-index: 1;
}

.v-icon:hover + p.arrow_box {
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

.title-update-icon {
  position: absolute !important;
  left: 142px;
  bottom: 10px;
}

.img-update-icon {
  position: absolute !important;
  left: 197px;
  bottom: 309px;
}

.speech-bubble {
  position: absolute;
  bottom: 105px;
  left: -30px;
  background: var(--speech-bubble-color);
  color: white;
  border-radius: 0.4em;
  padding: 0.3rem;
}

.speech-bubble:after {
  content: "";
  position: absolute;
  right: 0;
  top: 50%;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-left-color: var(--speech-bubble-color);
  border-right: 0;
  border-top: 0;
  margin-top: -5px;
  margin-right: -10px;
}

.speech-bubble2 {
  position: absolute;
  background: var(--speech-bubble-color);
  border-radius: 0.4em;
  padding: 0.3rem;
  color: white;
  top: -20%;
  left: 120%;
  width: 150px;
  text-align: center;
}

.speech-bubble-div {
  top: -3%;
  left: 105%;
  position: absolute;
}
.speech-bubble2:after {
  content: "";
  position: absolute;
  left: 0;
  top: 50%;
  width: 0;
  height: 0;
  border: 8px solid transparent;
  border-right-color: var(--speech-bubble-color);
  border-left: 0;
  border-bottom: 0;
  margin-top: -4px;
  margin-left: -8px;
}
.speech-bubble2-img {
  width: 100px;
}
.swal2-textarea {
  height: 5.75em !important;
  padding: 0.75em;
  resize: none;
  text-align: center;
  -ms-overflow-style: none;
  scrollbar-width: none;
}
.swal2-textarea::-webkit-scrollbar {
  display: none;
}
.mdi-camera::before {
    color: grey;
}
</style>

