<template>
  <transition name="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">
          <button class="btn_red_cancel" @click="$emit('close')">
            <span>X</span>
          </button>

          <div class="modal-header">
            <h2>링크 공유</h2>
          </div>

          <div class="modal-body">
            <a id="btnTwitter" class="link-icon twitter" @click="shareTwitter"
              >트위터</a
            >
            <a
              id="btnFacebook"
              class="link-icon facebook"
              @click="shareFacebook"
              >페이스북</a
            >
            <a id="btnKakao" class="link-icon kakao" @click="shareKakao"
              >카카오톡</a
            >
            <slot name="body"> </slot>
          </div>
          <div class="urlCopy">
            <v-text-field
              value="url"
              label="URL"
              readonly
              v-model="url"
            ></v-text-field
            ><v-btn
              depressed
              color="primary"
              style="margin-top: 12px; margin-left: 10px; width: 64px"
              @click="copyLink"
              >URL복사</v-btn
            >
          </div>
          <div class="modal-footer">
            <slot name="footer"> </slot>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
// import * as Alert from "@/api/alert"; //api 폴더 안에 넣어놓는 것이 맞는지는 모르겠음. But, 넣어놓을 곳이 딱히 없어서 넣어놓음
import { mapGetters } from "vuex";
import { KAKAO_API_KEY, FRONT_URL } from "@/config/index";

const userStore = "userStore";

export default {
  data() {
    return {
      url: "",
    };
  },

  computed: {
    ...mapGetters(userStore, {
      curUser: "getCurUser",
    }),
  },

  mounted() {
    this.setUrl();
  },

  methods: {
    setUrl() {
      this.url = FRONT_URL + "/eventRoom/" + this.curUser.id;
    },

    copyLink() {
      this.$emit("close");
      const copiedText = FRONT_URL + "/eventRoom/" + this.curUser.id;

      //https 적용시
      if (navigator.clipboard && window.isSecureContext) {
        // navigator clipboard api method'
        console.log("https 적용 시 copy")
        navigator.clipboard.writeText(`${copiedText}`);
      } else { //https 미적용시
        console.log("https 미적용 시 copy")
        // text area method
        let textArea = document.createElement("textarea");
        textArea.value = `${copiedText}`;
        // make the textarea out of viewport
        textArea.style.position = "fixed";
        textArea.style.left = "-999999px";
        textArea.style.top = "-999999px";
        document.body.appendChild(textArea);
        textArea.focus();
        textArea.select();
        new Promise((res, rej) => {
          // here the magic happens
          document.execCommand("copy") ? res() : rej();
          textArea.remove();
        });
      }
      // 복사완료 다이얼로그
      this.$swal.fire({
        icon: "success",
        title: "링크가 저장되었습니다.",
      });
    },

    shareTwitter() {
      var sendText = "UnPeu"; // 전달할 텍스트
      var sendUrl = this.url; // 전달할 URL
      window.open(
        "https://twitter.com/intent/tweet?text=" + sendText + "&url=" + sendUrl
      );
    },

    shareFacebook() {
      var sendUrl = this.url; // 전달할 URL
      window.open("http://www.facebook.com/sharer/sharer.php?u=" + sendUrl);
    },

    shareKakao() {
      // // // // // console.log("shareKakao-호출");
      // // // // // console.log(this.url);

      if (window.Kakao) {
        const kakao = window.Kakao;

        // 중복 initialization 방지
        if (!kakao.isInitialized()) {
          // 두번째 step 에서 가져온 javascript key 를 이용하여 initialize
          kakao.init(KAKAO_API_KEY);
        }

        window.Kakao.Link.sendDefault({
          objectType: "feed",
          content: {
            title: "UnPeu", // 보여질 제목
            description: "당신을 위한 조그마한 기록 그리고 선물", // 보여질 설명
            imageUrl:
              "https://user-images.githubusercontent.com/40678696/166247202-256f3c1e-8da0-4e28-a3e3-bf9a516c202a.gif", // 콘텐츠 URL
            link: {
              mobileWebUrl: `${this.url}`,
              webUrl: `${this.url}`,
            },
          },
        });
      }
    },
  },
};
</script>

<style scoped>
.link-icon {
  /* position: relative; */
  /* display: inline-block; */
  width: auto;
  font-size: 14px;
  font-weight: 500;
  font-family: hiffy;
  color: #333;
  /* margin-right: 10px; */
  padding-top: 50px;
}
.link-icon.twitter {
  width: 40px;
  background-image: url(@/assets/img/sns/icon-twitter.png);
  background-repeat: no-repeat;
}
.link-icon.facebook {
  width: 40px;
  background-image: url(@/assets/img/sns/icon-facebook.png);
  background-repeat: no-repeat;
}
.link-icon.kakao {
  width: 40px;
  background-image: url(@/assets/img/sns/icon-kakao.png);
  background-repeat: no-repeat;
}
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}
.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}
.modal-container {
  width: 340px;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  /* font-family: "Dancing Script", cursive; */
}
.modal-footer {
  font-family: sans-serif;
}
.modal-header {
  display: flex;
  align-items: center;
  justify-content: center !important;
}
.modal-header h2 {
  margin-top: 0;
  color: #505352;
  text-align: center;
  font-weight: bold;
}
.modal-body {
  margin: 10px 0;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: space-around !important;
}
.modal-default-button {
  float: right;
  background-color: transparent;
  border: 0;
}
.modal-enter {
  opacity: 0;
}
.modal-leave-active {
  opacity: 0;
}
.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
.modal-body,
.modal {
  color: #666 !important;
  /* font-family: "Nanumsquare", Tahoma, Geneva, Verdana, sans-serif; */
  font-size: 12px;
}

.warning {
  background-color: #e52d27;
  color: white;
  font-size: 12px;
  padding: 1rem;
}

table {
  width: 100%;
  /* border: 1px solid #f08986; */
  background-color: #f08986;
  border-collapse: collapse;
}
td {
  border: 1px solid white;
}

input[type="radio"] {
  /* display: inline-block; */
  /* float: left;*/
  display: none;
}
label {
  color: black;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* Hover */
label:hover {
  color: #666;
}

/* Checked */
input[type="radio"]:checked + label {
  color: white;
}
#unpeu_input {
  display: flex;
  justify-content: center;
}

.urlCopy {
  display: flex;
  font-family: hiffy;
}
</style>
