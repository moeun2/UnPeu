<template>
  <div class="main-room">
    <div class="event-tab">
      <ul class="myMenu" v-if="isMyPage">
        <li class="menu2">
          MENU
          <ul class="menu2_s submenu">
            <li><button>컨셉 변경</button></li>
            <li><button @click="resetMessage">컨셉 초기화</button></li>
            <li><button @click="saveMessage">메세지 저장</button></li>
          </ul>
        </li>
      </ul>
      <div v-else class="hide-menu"></div>
    </div>
    <div class="gift-box">
      <router-link :to="{ name: 'PresentPayment' }">
        <button class="reg-gift">
          <img src="https://i.imgur.com/vaBFer6.png" class="gift-img" alt="" />
          <p>
            어른이날을 축하해주세요
            <br />
            콩주머니 메세지 &amp; 선물 등록하러 가기!
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
            <img src="@/assets/img/pocket.png" class="pocket" alt="" />
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

    <!-- modal 창 -->
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
            <p> 메세지의 주인만 확인할 수 있어요 🤐 </p>
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
import { mapGetters } from "vuex";
const userStore = "userStore";
export default {
  name: "EventRoom",
  data() {
    return {
      messages: [],
      sender: "",
      content: "",
      imgUrl: "",
      API_BASE_URL: API_BASE_URL,
      // 몇개 씩 보여줄지
      perPage: 10,
      // 현재 페이지
      currentPage: 1,
      isMyPage: false,
    };
  },
  created() {
    if (window.localStorage.getItem("accessToken")) {
      //로그인 되어있는 상태 store inlogin true
      getUserDetailUseToken(
        window.localStorage.getItem("accessToken"),
        (res) => {
          console.log(res.data.User);
          this.$store.commit("userStore/setUser", res.data.User);
          this.isLogin = true;
          if (this.$route.params.userid == res.data.User.id) {
            this.isMyPage = true;
          }
        },
        () => {
          console.log("getUserDetailUseToken fail");
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
        console.log(res.data.Message);
        this.messages = res.data.Message;
        console.log(this.messages);
      },
      () => {
        console.log("fail");
      }
    );
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
    modal(message) {
        // 날짜 처리 (5월 5일 00:00:00 열람)
        let today = new Date();   
        let month = today.getMonth() + 1;  // 월
        let date = today.getDate();  // 일
        let hours = today.getHours(); // 시
        let minutes = today.getMinutes();  // 분
        let seconds = today.getSeconds();  // 초
        console.log(month + "/" + date + " " + hours + ":" + minutes + ":" + seconds);
        if(month >= 5 && date >= 5 && hours >= 0 && minutes >= 0 && seconds >= 0) {
            console.log("modal-message : ", message);
            this.content = message.content;
            this.sender = message.sender;
            if (message.present != null) {
                this.imgUrl = message.present.presentImg;
            } else {
                this.imgUrl = "";
            }
        }
        else {
            // 해당 날짜가 안 됐을 경우 모달 내용 변경, class 변경
            let noneView = document.getElementById('modal-content');
            noneView.innerHTML = '<p>아직 오픈 기간이 아닙니다. <br/> 조금만 더 기다려 주세요~! 🤩 </p>';
            noneView.className = 'modal-non-message';
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
              (res) => {
                console.log(res);
                Alert.resetMessageSuccess(this);
                getMessage(
                  this.curUser.id,
                  (res) => {
                    console.log(res.data.Message);
                    this.messages = res.data.Message;
                    console.log(this.messages);
                  },
                  () => {
                    console.log("fail");
                  }
                );
              },
              () => {
                console.log("fail");
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
        } else {
          if (this.messages.length == 0) {
            Alert.saveMessageFail(this);
          } else {
            saveMessage(
              this.messages,
              (res) => {
                console.log(res);
                Alert.saveMessageSuccess(this);
                getMessage(
                  this.curUser.id,
                  (res) => {
                    console.log(res.data.Message);
                    this.messages = res.data.Message;
                    console.log(this.messages);
                  },
                  () => {
                    console.log("fail");
                  }
                );
              },
              () => {
                console.log("fail");
              }
            );
          }
        }
      });
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
  width: 550px;
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
}
.gift-img {
  width: 35px;
  height: 35px;
  float: left;
}
.reg-gift {
  background-color: #fff;
  border-radius: 15px;
  padding: 10px;
  width: 400px;
  text-align: center;
  box-shadow: 3px 3px 3px #abababd8;
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
  width: 100px;
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
}

.message-box {
  border: 1px solid rgb(221, 239, 165);
  border-radius: 15px;
  white-space: pre-line;
}
/* pagination css */
.pg {
  width: 550px;
  bottom: -22%;
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

#modal-content {
    
}
</style>
