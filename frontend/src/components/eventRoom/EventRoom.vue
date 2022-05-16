<template>
  <div class="main-room" id="main-room">
    <div class="event-tab">
      <ul class="myMenu" v-if="isMyPage">
        <li class="menu2">
          MENU
          <ul class="menu2_s submenu">
            <li><a href="#setDate" class="menu-a">컨셉 변경</a></li>
            <li><button @click="resetMessage">메세지 삭제</button></li>
            <li><button @click="saveMessage">메세지 저장</button></li>
          </ul>
        </li>
      </ul>
      <div v-else class="hide-menu"></div>
    </div>

    <div class="gift-box">
      <router-link
        :to="{ name: 'PresentPayment', params: { userId: this.curUser.id } }"
      >
        <button class="reg-gift">
          <p>
            <span class="btn-p">📩 CLICK! CLICK! 🎁</span>
            <br />
            메세지 &amp; 선물 보내러 가기!
          </p>
        </button>
      </router-link>
    </div>
    <div v-if="this.messages.length == 0">
      <img src="@/assets/img/none.png" class="none-pocket" alt="" />
    </div>
    <div class="pocket-area" v-else>
      <div class="item" v-for="(message, index) in messagesFor" :key="index">
        <a href="#pocket">
          <button @click="modal(message)">
            <img class="pocket" alt="" />
          </button>
        </a>
      </div>
      <div class="pg">
        <v-pagination
          v-model="currentPage"
          :length="rows"
          @input="perPage"
          color="#a0deffe8"
        >
        </v-pagination>
      </div>
    </div>

    <!-- 메세지 modal 창 -->
    <div id="pocket" class="modal-window">
      <div class="modal-message">
        <a href="#">
          <button class="btn_red_cancel">
            <span>X</span>
          </button>
        </a>

        <!-- 로그인한 user의 모달창 -->
        <div class="modal-content" id="modal-content" v-if="isMyPage">
          <img
            :src="API_BASE_URL + imgUrl"
            alt=""
            class="modal-img"
            v-if="imgUrl"
          />
          <p class="message-user">{{ sender }}</p>
          <div class="message-box">{{ content }}</div>
        </div>

        <!-- guest의 모달창 -->
        <div class="modal-non-message" v-else>
          <p>메세지의 주인만 확인할 수 있어요 🤐</p>
        </div>
      </div>
    </div>

    <!-- 컨셉 변경 modal 창 -->
    <div id="setDate" class="modal-window">
      <div class="modal-message">
        <a href="#">
          <button class="btn_red_cancel">
            <span>X</span>
          </button>
        </a>
        <!-- 날짜 입력 받기 -->
        <div class="concept-content">
          <div class="mode-content">
            <h2 class="concept-h2">날짜 선택</h2>
            <p class="setDate-p">본 날짜는 매년 정기적으로 실행됩니다.
              <br/>
              현재 날짜를 기준으로 이미 지난 날짜를 설정할 경우 다음해로 이월됩니다.
            </p>

            <input type="number" name="month" class="input-date" id="month" v-model="month" placeholder="0" /> 월
            <input type="number" name="date" class="input-date" id="date" v-model="date" placeholder="0" /> 일
            <p class="setDate-p2">설정 날짜 : {{ this.year }}년 {{ this.month }}월 {{ this.date }}일 </p>
          </div>

          <div class="mode-content">
            <h2 class="concept-h2">컨셉 모드 선택</h2>
            <p class="setDate-p">
              컨셉을 바꾸게 되면 이전 컨셉의 내용이 모두 초기화 됩니다. 신중히
              선택하세요!
            </p>

            <input type="radio" name="concept" id="default" value="default" v-model="category" checked /> 기본
            <input type="radio" name="concept" id="birthday" value="birthday" v-model="category" /> 생일
            <input type="radio" name="concept" id="childrenDay" value="children" v-model="category" /> 어른이날

          </div>

          <button @click="setDate" type="submit" class="setDate-btn">
            설정
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getMessage, saveMessage, resetMessage } from "@/api/event.js";
import { API_BASE_URL } from "@/config/index.js";
import { getUserDetailUseToken } from "@/api/user.js";
import * as Alert from "@/api/alert";
import { mapActions, mapGetters, mapMutations } from "vuex";
import dayjs from "dayjs";
const userStore = "userStore";
const presentStore = "presentStore";

export default {
  name: "EventRoom",
  component: {
    dayjs,
  },
  data() {
    return {
      messages: [],
      sender: "",
      content: "",
      imgUrl: "",
      API_BASE_URL: API_BASE_URL,
      // 몇개 씩 보여줄지
      perPage: 12,
      // 현재 페이지
      currentPage: 1,
      isMyPage: false,
      month: "",
      date: "",
      year: "",
      today: dayjs().format("YYYY-MM-DD"),
      category: "",
      selectedDate: "",
      data: {
        userId: "",
        category: "",
        selectedDate: "",
      },
    };
  },

  created() {
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
            this.year = res.data.User.selectedDate[0];
            this.month = res.data.User.selectedDate[1];
            this.date = res.data.User.selectedDate[2];
            this.category = res.data.User.category;
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
  },

  mounted() {
    getMessage(
      this.curUser.id,
      (res) => {
        // console.log(res.data.Message);
        this.messages = res.data.Message;
        // console.log(this.messages);
      },
      () => {
        console.log("get Message fail");
      }
    );
  },

  watch: {
    $route(to, from) {
      //라우터 파라미터 변경 감지
      if (to.path !== from.path) this.changeParams(this.$route.params.userid);
    },
  },

  computed: {
    ...mapGetters(userStore, {
      curUser: "getCurUser",
    }),
    rows() {
      let length = this.messages.length / this.perPage;
      return Math.ceil(length);
    },
    messagesFor() {
      const items = this.messages;
      return items.slice(
        (this.currentPage - 1) * this.perPage,
        this.currentPage * this.perPage
      );
    },
  },

  methods: {
    ...mapMutations(presentStore, ["RESET_PRESENT_LIST"]),
    ...mapActions(userStore, ["AC_UPDATE_CONCEPT"]),

    changeParams(userId) {
      //파라미터 변경시 실행
      this.isMyPage = true;
      getMessage(
        userId,
        (res) => {
          this.messages = res.data.Message;
        },
        () => {}
      );
    },

    modal(message) {
      if (this.curUser.selectedDate == this.today) {
        this.content = message.content;
        this.sender = message.sender;
        if (message.presentId != null) {
          this.imgUrl = message.presentImg;
        } else {
          this.imgUrl = "";
        }
      } else {
        // 해당 날짜가 안 됐을 경우 모달 내용 변경, class 변경
        let noneView = document.getElementById("modal-content");
        noneView.innerHTML =
          "<p>아직 오픈 기간이 아닙니다. <br/> 오픈날까지 조금만 기다려 주세요~! 🤩🤩🤩 </p>";
        noneView.className = "modal-non-message";
      }
    },

    resetMessage() {
      this.$swal.fire(Alert.resetMessageCheck).then((result) => {
        if (result.dismiss === this.$swal.DismissReason.cancel) {
          Alert.resetMessageCancel(this);
        } else {
          if (this.messages.length == 0) {
            Alert.resetMessageFail(this);
          } else {
            resetMessage(
              () => {
                // console.log(res);
                Alert.resetMessageSuccess(this);
                getMessage(
                  this.curUser.id,
                  (res) => {
                    // console.log(res.data.Message);
                    this.RESET_PRESENT_LIST();
                    this.messages = res.data.Message;
                    // console.log(this.messages);
                  },
                  () => {
                    console.log("get Message fail");
                  }
                );
              },
              () => {
                console.log("Message reset fail");
              }
            );
          }
        }
      });
    },

    saveMessage() {
      this.$swal.fire(Alert.saveMessageCheck).then((result) => {
        if (result.dismiss === this.$swal.DismissReason.cancel) {
          Alert.saveMessageCancel(this);
          console.log(this.messages);
        } else {
          if (this.messages.length == 0) {
            Alert.saveMessageFail(this);
          } else {
            saveMessage(
              this.messages,
              () => {
                // console.log(res);
                Alert.saveMessageSuccess(this);
                getMessage(
                  this.curUser.id,
                  (res) => {
                    // console.log(res.data.Message);
                    this.messages = res.data.Message;
                    // console.log(this.messages);
                  },
                  () => {
                    console.log("get Message fail");
                  }
                );
              },
              () => {
                console.log("save Message fail");
              }
            );
          }
        }
      });
    },
    setDate() {
      if (this.month == null) {
        this.$swal.fire("Oops...!", "Month를 적어주세요!", "error");
      } else if (this.date == null) {
        this.$swal.fire("Oops...!", "date을 적어주세요!", "error");
      } else if (this.month < 1 || this.month > 12) {
        this.$swal.fire("Oops...!", "Month는 1 ~ 12월까지 있어요!", "error");
      } else if (this.month == 2 && (this.date < 1 || this.date > 28)) {
        this.$swal.fire("Oops...!", "2월의 일 범위를 벗어났어요!", "error");
      } else if (
        (this.month == 1 ||
          this.month == 3 ||
          this.month == 5 ||
          this.month == 7 ||
          this.month == 8 ||
          this.month == 10 ||
          this.month == 12) &&
        (this.date < 1 || this.date > 31)
      ) {
        this.$swal.fire("Oops...!", "일 범위를 벗어났어요!", "error");
      } else if (
        (this.month == 4 ||
          this.month == 6 ||
          this.month == 9 ||
          this.month == 11) &&
        (this.date < 1 || this.date > 30)
      ) {
        this.$swal.fire("Oops...!", "일 범위를 벗어났어요!", "error");
      } else {
        let today = new Date();
        let year = today.getFullYear();
        // 현재 날짜와 비교해서 적은 날짜이면 연도 + 1
        if(this.month < today.getMonth()+1) {
          year = year + 1;
        }else if(this.month == today.getMonth() +1) {
          if(this.date < today.getDate()) {
            year = year + 1;
          }
        }
        let month2 = "";
        let date2 = "";
        // 한자리 수 일 경우
        if (this.month < 10) {
          month2 = "0" + this.month;
        } else {
          month2 = this.month;
        }

        if (this.date < 10) {
          date2 = "0" + this.date;
        } else {
          date2 = this.date;
        }

        if (this.category === "children") {
          if(this.month > 5) {
            year = year + 1;
          }else if(this.month == 5) {
            if(this.date > 5) {
              year = year + 1;
            }
          }
          this.month = 5;
          this.date = 5;
          month2 = "05";
          date2 = "05";
        }
        this.selectedDate = year + "-" + month2 + "-" + date2;
        let data = {};
        data.category = this.category;
        data.selectedDate = this.selectedDate;
        data.userId = this.curUser.id;
        //console.log(data);

        this.$swal
          .fire({
            title: "컨셉 변경 저장",
            text: "설정된 날짜는 0시 정각에 실행됩니다.",
            icon: "warning",
            showCancelButton: true,
            confirmButtonText: "할래요!",
            cancelButtonText: "안할래요!",
            reverseButtons: false,
          })
          .then((result) => {
            if (result.isConfirmed) {
              this.AC_UPDATE_CONCEPT(
                data,
                function (res) {
                  console.log(res);
                },
                function () {}
              );
              resetMessage(
              () => {
                // console.log(res);
                Alert.resetMessageSuccess(this);
                getMessage(
                  this.curUser.id,
                  (res) => {
                    // console.log(res.data.Message);
                    this.RESET_PRESENT_LIST();
                    this.messages = res.data.Message;
                    // console.log(this.messages);
                  },
                  () => {
                    console.log("get Message fail");
                  }
                );
              },
              () => {
                console.log("Message reset fail");
              }
            );
              this.$router.go({ name: "eventRoom" }).catch(() => {});
            }
          });
      }
    },
  },
};
</script>

<style lang="scss">
.pocket-area {
  margin-top: 40%;
  margin-left: 20px;
  padding-right: 20px;
  display: grid;
  grid-template-rows: repeat(auto-fill, minmax(90px, 1fr));
  grid-template-columns: repeat(auto-fill, minmax(90px, 1fr));
  width: 630px;
  height: 230px;
}
.item {
  display: flex;
}
.none-pocket {
  width: 575px;
}
.pocket {
  width: 85px;
  height: 80px;
  content: var(--pocket);
}
.gift-img {
  width: 35px;
  height: 35px;
  float: left;
  content: url("https://i.imgur.com/nupfePY.png");
}
.reg-gift {
  background-color: #fff;
  border-radius: 15px;
  padding: 10px;
  width: 400px;
  text-align: center;
  box-shadow: 3px 3px 3px #abababd8;
}
.reg-gift:hover {
  background-color: rgb(222, 221, 221);
  // border-radius: 15px;
  // padding: 10px;
  // width: 400px;
  // text-align: center;
  // box-shadow: 3px 3px 3px #abababd8;
}
.gift-box {
  text-align: center;
}
.event-tab {
  display: block;
  text-align: right;
}
.event-btn {
  width: 30px;
  height: 30px;
}

/* 드롭다운 메뉴 css */
ul.myMenu {
  list-style: none;
  margin: 5px;
}
ul.myMenu > li {
  display: inline-block;
  margin-top: 5px;
  width: 80px;
  padding: 5px 10px;
  text-align: center;
  position: relative;
}
ul.myMenu > li:hover {
  background: rgba(255, 255, 255, 0.627);
  border-radius: 30px;
}
ul.myMenu > li ul.submenu {
  display: none;
  position: absolute;
  top: 30px;
  left: 0;
}
ul.myMenu > li:hover ul.submenu {
  display: block;
}
ul.myMenu > li ul.submenu > li {
  display: inline-block;
  width: 90px;
  padding: 5px 10px;
  background: #eee;
  border: 1px solid #eee;
  text-align: center;
}
ul.myMenu > li ul.submenu > li:hover {
  background: #fff;
}
.hide-menu {
  height: 36px;
}

/* modal창 css */

.modal-window {
  position: fixed;
  background-color: rgba(15, 15, 15, 0.25);
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: 999;
  visibility: hidden;
  opacity: 0;
  pointer-events: none;
  transition: all 0.3s;
  &:target {
    visibility: visible;
    opacity: 1;
    pointer-events: auto;
  }
  & > div {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    background-size: cover;
    padding: 7px;
  }
  header {
    font-weight: bold;
  }
  h1 {
    font-size: 150%;
    margin: 0 0 15px;
  }
}

.modal-close {
  color: black;
  line-height: 50px;
  font-size: 100%;
  position: absolute;
  right: 10%;
  text-align: center;
  top: 10%;
  width: 70px;
  text-decoration: none;
  &:hover {
    color: black;
  }
}

.modal-message {
  background-color: #fff;
  border-radius: 15px;
  width: 300px;
}

.modal-non-message {
  font-size: 25px;
  font-weight: bold;
  text-align: center;
  padding-top: 20px;
  padding-bottom: 20px;
}
.modal-content {
  text-align: center;
}

.modal-img {
  width: 200px;
  height: 200px;
}

.message-user {
  font-size: 20px;
  font-weight: bold;
  padding-bottom: 5px;
  word-wrap: break-word;
}

.message-box {
  border: 1px solid rgb(221, 239, 165);
  border-radius: 15px;
  white-space: pre-line;
  word-wrap: break-word;
}
/* pagination css */
.pg {
  width: 600px;
  bottom: -29%;
  position: absolute;
}

.v-pagination__item {
  border-radius: 20px !important;
  height: 31px !important;
  min-width: 31px !important;
}

#pocket_user {
  position: absolute;
  padding-top: 20px;
  padding-left: 12px;
  text-align: center;
}

.menu-a {
  color: black;
}
.concept-room {
  background-color: #ddefa5;
  height: 435px;
  padding: 23px;
}
.concept-div {
  background-color: #fff;
  height: 390px;
  border-radius: 5px;
}
.concept-content {
  padding: 10px;
}
.setDate-p {
  font-size: 14px;
  margin-bottom: 10px;
  color: rgb(111, 111, 111);
}
.setDate-p2 {
  margin-top: 10px;
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 10px;
  color: rgb(111, 111, 111);
}
.input-date {
  height: 30px;
  width: 50px;
  border-bottom: solid rgb(37, 37, 37) 1px;
}
.setDate-btn {
  float: right;
  width: 60px;
  height: 30px;
  color: #fff;
  border-radius: 15px;
  background-color: #4caf50;
}
.concept-h2 {
  font-size: 20px;
  margin-top: 10px;
  font-weight: bold;
}
.mode-btn {
  width: 70px;
  height: 30px;
  color: #000;
  border-radius: 15px;
  background-color: #dce775;
  margin-right: 5px;
  margin-top: 10px;
}
.mode-content {
  margin-bottom: 30px;
}
.btn-p {
  font-size: 20px;
  background-color: #dce775;
}
</style>
