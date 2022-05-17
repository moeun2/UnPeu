<template>
  <div class="view">
    <!-- @click.once="playMusic(userMusic)" -->
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
                  <div class="text-today-is">
                    BGM IS .. <span v-text="userMusic"> </span>
                    <div class="music-icon">
                      <v-icon
                        small
                        @click="playMusic(userMusic)"
                        v-if="userMusic != 'none'"
                        >mdi-play</v-icon
                      >
                      <v-icon
                        small
                        @click="pauseMusic"
                        v-if="userMusic != 'none'"
                        >mdi-pause</v-icon
                      >
                      <v-icon small @click="updateMusic" v-if="isMyPage"
                        >mdi-account-music</v-icon
                      >
                    </div>
                  </div>
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
                    <a class="info-name" @click="copyLink">
                      {{ this.userName }}</a
                    >
                    <div class="info-birth" v-if="isAgree">
                      {{ this.userBirth }}
                    </div>
                    <br />
                    <p class="text-email" v-if="isAgree">
                      {{ this.userEmail }}
                    </p>
                    <p class="text-email" v-if="!isAgree">ㅤ</p>
                    <div style="display: flex; margin-top: 10px">
                      <router-link
                        :to="{ name: 'PresentManage' }"
                        v-if="isMyPage"
                      >
                        <button class="item">
                          <v-icon>mdi-gift-open</v-icon>
                          <p class="arrow_box">받고 싶은<br />선물 등록!</p>
                        </button>
                      </router-link>

                      <router-link :to="{ name: 'Login' }" v-if="!isLogin">
                        <button class="item">
                          <v-icon class="v-icon">mdi-login</v-icon>
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
                        <p class="arrow_box">로그아웃</p>
                      </button>
                      <button
                        class="item"
                        @click="userSetting"
                        v-if="isMyPage && isLogin"
                        style="margin-left: 5px"
                      >
                        <v-icon>mdi-account-cog</v-icon>
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
              <a :href="this.url" target='_blank' style="color: white">
                더 상세한 사용방법과<br />이벤트를 알고싶다면?<br /><strong>(👉Click
                Here!👈)</strong></a
              >
            </div>
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
                    >
                      <li :class="[activeCheckClass]" @click="checkHome()">
                        홈
                      </li></router-link
                    >
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
  updateUserMusic,
} from "@/api/user.js";
import { EVENT_URL, FRONT_URL, API_BASE_URL } from "@/config/index";
import LinkShareModal from "@/components/option/LinkShareModal.vue";
import { mapGetters, mapMutations, mapActions } from "vuex";
import flatpickr from "flatpickr";
import "flatpickr/dist/flatpickr.min.css";
const userStore = "userStore";
const presentStore = "presentStore";
import dayjs from "dayjs";
// import store from '@/store';
export default {
  name: "App",
  component: {
    dayjs,
  },
  data() {
    return {
      today: dayjs().format("YYYY-MM-DD"),
      url: EVENT_URL,
      activeCheckClass: "menu-item mi-1 menu-checked",
      activeClass: "menu-item mi-3",
      userName: "김싸피",
      userInfo: "선물주는 사람\n차칸 사람",
      userTitle: "개성있는 타이틀을 설정해보세요 :)",
      userImg: "",
      userBirth: "1996.10.31",
      userEmail: "ssafykim@ssafy.com",
      userMusic: "none",
      isLogin: false,
      isMyPage: false,
      isAgree: false,
      totalVisit: 0,
      todayVisit: 0,
      showModal: false,
      audio: null,
      rules: [
        (value) =>
          !value ||
          value.size < 2000000 ||
          "Avatar size should be less than 2 MB!",
      ],
      files: null,
      data: {
        userId: "",
        category: "",
        selectedDate: "",
      },
    };
  },
  watch: {
    files: function () {
      this.updateUserImg();
    },
    $route(to, form) {
      if (to.path !== form.path) {
        this.checkPath();
        this.changeParams(this.$route.params.userid);
      }
    },
  },
  computed: {
    ...mapGetters(userStore, {
      curUser: "getCurUser",
    }),
  },
  mounted() {
    this.checkConcept();
    
  },
  created() {
    
    // this.checkConcept();
    if (window.localStorage.getItem("accessToken")) {
      //로그인 되어있는 상태 store inlogin true
      getUserDetailUseToken(
        window.localStorage.getItem("accessToken"),
        (res) => {
          console.log("UserData : ", res.data.User);
          this.$store.commit("userStore/setUser", res.data.User);

          this.isLogin = true;
          if (this.$route.params.userid == res.data.User.id) {
            this.isMyPage = true;
          }
        },
        () => {
          this.isLogin = false;
          window.localStorage.removeItem("accessToken");

          this.$router.go;
        }
      );
    }
    this.setUserData();
    this.checkPath();

    //조회수 증가
    if (
      window.location.href ==
      FRONT_URL + "/eventRoom/" + this.$route.params.userid
    ) {
      const nowVisit =
        this.$route.params.userid + new Date().toLocaleDateString("en-US");
      if (
        !sessionStorage.getItem("recentVisit") ||
        sessionStorage.getItem("recentVisit") != nowVisit
      ) {
        increaseVisit(this.$route.params.userid, (res) => {
          if (res.data.User.todayVisit) {
            this.todayVisit = res.data.User.todayVisit;
          }
          if (res.data.User.totalVisit) {
            this.totalVisit = res.data.User.totalVisit;
          }
          sessionStorage.setItem("recentVisit", nowVisit);
        });
      }
    }
  },
  components: {
    LinkShareModal,
  },

  methods: {
    ...mapActions(userStore, ["AC_UPDATE_CONCEPT","AC_USER_DETAIL"]),
    playMusic(music) {
      // alert("노래를 재생합니다.")
      if (music != "none") {
        if (this.audio) {
          if (!this.audio.paused) {
            this.audio.pause();
            this.audio.currentTime = 0;
          }
          this.audio.play();
        } else {
          // console.log(this.userMusic)
          const audio = new Audio(require("@/assets/music/" + music + ".mp3"));
          audio.loop = true;
          audio.volume = 0.07;
          this.audio = audio;
          this.audio.play();
        }
      }
    },
    pauseMusic() {
      if (this.audio) {
        this.audio.pause();
      }
    },
    checkPath(){
      const path = this.$route.path;
      if(path.startsWith("/eventRoom")){
        this.checkHome()
      }else if(path.startsWith("/diary")){
        this.checkDiary();
      }
    },
    checkConcept() {
      let concept = this.curUser.category;
      let selectedDate = this.curUser.selectedDate;
      // console.log("selectedDate : ", selectedDate);
      // console.log("today : ", this.today);
      // console.log("CheckConcept-Concept : ", concept);
      switch (concept) {
        case "default":
          if (selectedDate <= this.today) {
            document.documentElement.setAttribute(
              "color-theme",
              "default-open"
            );
          } else {
            document.documentElement.setAttribute(
              "color-theme",
              "default-close"
            );
          }
          break;
        case "birthday":
          if (selectedDate <=  this.today) {
            document.documentElement.setAttribute(
              "color-theme",
              "birthday-open"
            );
          } else {
            document.documentElement.setAttribute(
              "color-theme",
              "birthday-close"
            );
          }
          break;
        case "children":
          if (selectedDate <=  this.today) {
            document.documentElement.setAttribute(
              "color-theme",
              "children-open"
            );
          } else {
            document.documentElement.setAttribute(
              "color-theme",
              "children-close"
            );
          }
          break;
        default:
          console.log(concept);
          console.log("어떤 값인지 파악이 되지 않습니다.");
      }
    },
    ...mapMutations(presentStore, ["RESET_PRESENT_LIST"]),
    goToMainPage() {
      this.$router.push({ name: "eventRoom" }).catch(() => {});
      this.checkHome();
    },
    setUserData() {
      getUserDetail(
        this.$route.params.userid,
        (res) => {
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
            this.userTitle = "개성있는 타이틀을 설정해보세요 :)";
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
          this.isAgree = res.data.User.isAgree;

          if (res.data.User.userMusic) {
            if (this.audio && this.userMusic != res.data.User.userMusic) {
              this.pauseMusic();
              this.audio = null;
              // this.playMusic(res.data.User.userMusic)
            }
            this.userMusic = res.data.User.userMusic;
          }
        },
        () => {
          this.$router.push({ name: "NotFound" });
        }
      );
    },

    async changeParams(index) {
      if (window.localStorage.getItem("accessToken")) {
        //로그인 되어있는 상태 store inlogin true
        await getUserDetailUseToken(
          window.localStorage.getItem("accessToken"),
          (res) => {
            this.$store.commit("userStore/setUser", res.data.User);
            this.isLogin = true;
            if (index == res.data.User.id) {
              this.isMyPage = true;
            }
          },
          () => {
            this.isLogin = false;
            window.localStorage.removeItem("accessToken");
            this.$store.commit("userStore/setUserNull");
            this.$router.go;
          }
        );
      }
      //지금 접속한 페이지유저의 정보
      this.setUserData();
      this.AC_USER_DETAIL(index)

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
    async updateMusic() {
      const { value: music } = await this.$swal.fire({
        title: "BGM🎵",
        input: "select",
        text: "원하는 노래를 선택해주세요!",
        inputOptions: {
          none: "노래를 재생하고 싶지 않아요",
          allthat: "allthat",
          betterdays: "betterdays",
          happiness: "happiness",
          highoctane: "highoctane",
          jazzcomedy: "jazzcomedy",
          moose: "moose",
        },
        inputPlaceholder: "노래를 선택해주세요",
        showCancelButton: true,
      });
      if (music) {
        updateUserMusic(music, (res) => {
          this.userMusic = res.data.User.userMusic;
          this.pauseMusic();
          this.audio = null;
          this.playMusic(this.userMusic);
        });
      }
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
          spellcheck: "false",
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
        input: "textarea",
        inputValue: this.userInfo,
        inputLabel: "프로필 사진 밑의 소개글입니다. 여러분을 소개해주세요 :)",
        inputPlaceholder: "50자 이하, 4줄 이하로 작성해주세요.",
        inputAttributes: {
          maxlength: 50,
          rows: 4,
          spellcheck: "false",
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

    async userSetting() {
      let flatpickrInstance;

      await this.$swal
        .fire({
          title: "회원 정보",
          icon: "info",
          html:
            '<div>이메일:<input input type="email" placeholder="이메일을 입력해주세요" id="email" class="swal2-input" value=' +
            this.userEmail +
            "></div>" +
            '<div>생 일 :<input placeholder="생일을 입력해주세요" class="swal2-input" id="birth" value=' +
            this.userBirth +
            "></div><br/>" +
            '<label><input type="checkbox" id="isAgree" name="scales" ' +
            (this.isAgree ? "checked" : "") +
            ">ㅤ생일과 이메일을 공개하는 것에 동의합니다.</label>",

          inputLabel:
            "여러분의 이메일과 생일을 입력해주세요. 드디어 마이페이지가 생성됩니다 :)",
          stopKeydownPropagation: false,
          focusConfirm: true,
          showDenyButton: true,
          showCancelButton: true,
          cancelButtonText: "취소",
          confirmButtonText: "수정",
          denyButtonText: `회원 탈퇴`,
          preConfirm: () => {
            var exptext =
              /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
            if (!flatpickrInstance.selectedDates[0]) {
              this.$swal.showValidationMessage(`생일을 입력해주세요`);
            } else if (flatpickrInstance.selectedDates[0] > new Date()) {
              this.$swal.showValidationMessage(
                `혹시.. 아직 안태어나셨나요? 생일을 올바르게 입력해주세요 :)`
              );
            }
            if (
              !document.getElementById("email").value ||
              !exptext.test(document.getElementById("email").value)
            ) {
              this.$swal.showValidationMessage(`이메일을 입력해주세요`);
            }
          },
          willOpen: () => {
            flatpickrInstance = flatpickr(
              this.$swal.getPopup().querySelector("#birth"),
              {
                allowInput: true,
              }
            );
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            const data = {
              userEmail: document.getElementById("email").value,
              userBirth: document.getElementById("birth").value,
              isAgree: document.getElementById("isAgree").checked,
            };
            updateUserEmailBirth(
              localStorage.getItem("accessToken"),
              data,
              (res) => {
                // console.log("success change email and birth")
                this.$swal.fire("수정을 성공했습니다!", "", "success");
                this.userEmail = res.data.User.userEmail;
                this.userBirth =
                  res.data.User.userBirth[0] +
                  "." +
                  res.data.User.userBirth[1] +
                  "." +
                  res.data.User.userBirth[2];
                this.isAgree = res.data.User.isAgree;
              }
            );
          } else if (result.isDenied) {
            this.$swal
              .fire({
                title: "정말 회원을 탈퇴하시겠습니까?",
                icon: "question",
                text: "탈퇴하면 소중한 메세지와 선물들이 사라집니다 그래도 탈퇴하시겠습니까?",
                showCancelButton: true,
              })
              .then((result) => {
                if (result.isConfirmed) {
                  deleteUser(() => {
                    this.$swal.fire("회원 탈퇴되었습니다.", "", "success");
                    localStorage.removeItem("accessToken");
                    this.$store.commit("userStore/setUSerNull");
                    this.$router.push({ name: "Landing" });
                  });
                }
              });
          }
        });
    },
  },
};
</script>

<style scoped>
* {
  font-family: "GangwonEdu_OTFBoldA" !important;
}
@font-face {
  font-family: "GangwonEdu_OTFBoldA";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/GangwonEdu_OTFBoldA.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
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
  bottom: 100px;
  left: -50px;
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

a strong { 
  font-weight: bold;
  color: blue;
}
.speech-bubble2 {
  position: absolute;
  background: var(--speech-bubble-color);
  border-radius: 0.4em;
  padding: 0.3rem;

  top: -20%;
  left: 120%;
  width: 150px;
  text-align: center;
}
.speech-bubble2 a{
  color: white !important;
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
.swal2-select {
  border: 1px solid #d9d9d9;
}
.music-icon {
  float: right;
}
</style>
