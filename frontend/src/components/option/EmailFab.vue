<template>
  <div class="ba-we-love-subscribers-wrap">
    <div class="ba-we-love-subscribers popup-ani">
      <header>
        <h1>당신을 위한 조그마한 기록 그리고 선물<br/>UnPeu:앙뿌</h1>
        <p>
          만약 더 디테일한 피드백을 주시고 싶다면
          <a href="https://forms.gle/uXoLCauCEHMNoPoo6"
            ><strong>여기클릭!</strong></a
          >
        </p>
        <p>경품추천을 위하여 MM 아이디를 꼭! 입력해주세요!</p>
      </header>
      <!-- <form v-on:submit="submitEmail"> -->
      <div class="formControl">
        <textarea
          name="email"
          placeholder="피드백 부탁드려요♥ (한 줄 피드백도 환영!!)"
          type="text"
          value=""
          v-model="content"
        /><br />
        <input
          class="logo-ani"
          name="submit"
          type="submit"
          @click="submitEmail"
        />
        <!-- </form> -->
      </div>
    </div>
    <div class="ba-we-love-subscribers-fab" @click="clcickFab">
      <div class="wrap">
        <div class="img-fab img"></div>
      </div>
    </div>
  </div>
</template>
<script>
import $ from "jquery";
import { sendMail } from "@/api/mail.js";
import * as Alert from "@/api/alert";

export default {
  data: () => ({
    content: null,
  }),

  methods: {
    clcickFab() {
      $(".ba-we-love-subscribers-fab .wrap").toggleClass("ani");
      $(".ba-we-love-subscribers").toggleClass("open");
      $(".img-fab.img").toggleClass("close");
    },

    submitEmail() {
      if (this.content != null) {
        //alert(this.content);
        const vm = this;
        sendMail(
          this.content,
          function () {
            Alert.sendFeedbackSuccess(vm);
            // console.log(response);
            vm.content = null;
            vm.clcickFab();
          },
          function (err) {
            Alert.sendFeedbackFailure(this);
            console.log(err);
          }
        );
      } else {
        // alert("내용을 입력해주세요");
        this.$swal.fire("Oops!", "내용을 입력해주세요~!", "error");
      }
    },
  },
};
</script>
<style scoped>


.ba-we-love-subscribers {
  width: 290px;
  height: 50px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0px 12px 45px rgba(0, 0, 0, 0.15);
  font-family: "hiffy" !important;
  text-align: center;
  margin: 0 0 10px 0;
  overflow: hidden;
  opacity: 0;

}
.ba-we-love-subscribers.open {
  height: 350px;
  opacity: 1;
}
.ba-we-love-subscribers.popup-ani {
  -webkit-transition: all 0.8s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  transition: all 0.8s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}
.ba-we-love-subscribers h1 {
  font-size: 20px;
  color: #757575;
  padding: 25px 0;
  margin: 0;
  font-weight: 400;
  font-family: "hiffy" !important;
}
.ba-we-love-subscribers .love {
  width: 20px;
  height: 20px;
  background-position: 35px 84px;
  display: inline-block;
  margin: 0 6px;
  background-size: 62px;
}
.ba-we-love-subscribers .ba-logo {
  width: 65px;
  height: 25px;
  background-position: 0px;
  margin: 0 auto;
  opacity: 0.5;
  cursor: pointer;
}
.ba-we-love-subscribers .ba-logo:hover {
  opacity: 1;
}
.logo-ani {
  transition: 0.5s linear;
  -webkit-transition: 0.5s linear;
}
.ba-we-love-subscribers textarea {
  font-size: 14px;
  padding: 12px 15px;
  border-radius: 15px;
  border: 0;
  outline: none;
  margin: 8px 0;
  width: 100%;
  box-sizing: border-box;
  line-height: normal;
}
.ba-we-love-subscribers input {
  font-size: 16px;
  padding: 12px 15px;
  border-radius: 15px;
  border: 0;
  outline: none;
  margin: 8px 0;
  width: 100%;
  box-sizing: border-box;
  line-height: normal;
}
.formControl {
  padding: 5px 30px 0;
  margin-bottom: 15px;
}
.ba-we-love-subscribers textarea {
  background-color: #eee;
  height: 80px;
  font-size: 16px;
  overflow-x: hidden;
  overflow-y: scroll;
  resize: none;
}
.ba-we-love-subscribers input[name="submit"] {
  background-color: var(--profile);
  cursor: pointer;
  color: #fff;
}
.ba-we-love-subscribers input[name="submit"]:hover {
  background-color:var(--profile);
}
.ba-we-love-subscribers .img {
  background-image: url("https://4.bp.blogspot.com/-1J75Et4_5vc/WAYhWRVuMiI/AAAAAAAAArE/gwa-mdtq0NIqOrlVvpLAqdPTV4VAahMsQCPcB/s1600/barrel-we-love-subscribers-img.png");
}
.ba-we-love-subscribers-fab {
  width: 65px;
  height: 65px;
  background-color: var(--profile);
  border-radius: 30px;
  float: right;
  box-shadow: 0px 12px 45px rgba(0, 0, 0, 0.3);
  z-index: 5;
  position: relative;
}
.ba-we-love-subscribers-fab .img-fab {
  height: 30px;
  width: 30px;
  margin: 15px auto;
  background-image: url("https://4.bp.blogspot.com/-1J75Et4_5vc/WAYhWRVuMiI/AAAAAAAAArE/gwa-mdtq0NIqOrlVvpLAqdPTV4VAahMsQCPcB/s1600/barrel-we-love-subscribers-img.png");
  background-position: -1px -53px;
}
.ba-we-love-subscribers-fab .wrap {
  transform: rotate(0deg);
  -webkit-transition: all 0.15s cubic-bezier(0.15, 0.87, 0.45, 1.23);
  transition: all 0.15s cubic-bezier(0.15, 0.87, 0.45, 1.23);
}
.ba-we-love-subscribers-fab .ani {
  transform: rotate(45deg);
  -webkit-transition: all 0.15s cubic-bezier(0.15, 0.87, 0.45, 1.23);
  transition: all 0.15s cubic-bezier(0.15, 0.87, 0.45, 1.23);
}
.ba-we-love-subscribers-fab .close {
  background-position: -2px 1px;
  transform: rotate(-45deg);
  float: none;
  /*Bootstrap Overide*/
  opacity: 1;
  /*Bootstrap Overide*/
}
.ba-we-love-subscribers-wrap {
  position: fixed;
  right: 25px;
  bottom: 25px;
  z-index: 1000;
}
.ba-settings {
  position: absolute;
  top: -25px;
  right: 0px;
  padding: 10px 20px;
  background-color: #555;
  border-radius: 5px;
  color: #fff;
}
</style>
