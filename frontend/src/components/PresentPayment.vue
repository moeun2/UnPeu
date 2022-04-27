<template>
  <div>
    <div class="message-title">콩주머니 보내기</div>
    <present-select-list-search @present="getPresent" />
    <present-message @message="getMessage" />
    <button @click="testAlert">Hello world</button>
  </div>
</template>

<script>
import PresentMessage from "./present/PresentMessage.vue";
import PresentSelectListSearch from "@/components/present/PresentSelectListSearch.vue";
import { mapActions } from "vuex";
import * as Alert from "@/api/alert"; //api 폴더 안에 넣어놓는 것이 맞는지는 모르겠음. But, 넣어놓을 곳이 딱히 없어서 넣어놓음
const presentStore = "presentStore";
export default {
  components: { PresentMessage, PresentSelectListSearch },
  data() {
    return {
      message: {
        category: "2022_어른이날",
        presentId: null,
        price: null,
        sender: "",
        content: "",
        userId: 1,
      },
      currentIdx: "",
    };
  },
  methods: {
    ...mapActions(presentStore, ["sendPresentMessage"]),
    testAlert(){
      this.checkPay();
    },
    /**
     * 선물결제에 관해 Alert로 물어보고 sendPresentMessage API를 실행
     */
    checkPresent() {
      if (this.presentId == null) {
        this.$swal.fire(Alert.notSelectPresentBody).then((result) => {
          if (result.dismiss === this.$swal.DismissReason.cancel) {
            this.changeCardColor(false);
          } 
        });
      }else{
        this.$swal
        .fire(Alert.agreePaymentBody)
        .then((result) => {
          if (result.isConfirmed) {
            this.checkPay();
          } else if (result.dismiss === this.$swal.DismissReason.cancel) {
            Alert.paymentCancel(this);
            this.message.presentId = null;
            this.message.price = null;
          }
        });
      }
    },
    /**
     * PresentSelectListSearch 모달에서 Emit온 Present를 받는 함수
     */
    getPresent(data) {
      this.message.presentId = data.selectedPresentId;
      this.message.price = data.selectedPresentPrice;
      this.currentIdx = data.selectedCardIdx;
      console.log("getPresent - ", this.message);
      console.log(this.currentIdx);
    },
    /**
     * PresentMessage 모달에서 Emit온 Message를 받는 함수
     */
    getMessage(data) {
      this.message.sender = data.sender;
      this.message.content = data.content;
      console.log("getMessage - ", this.message);
      this.checkPresent();
      // this.checkPay();
      //this.sendPresentMessage(this.message);
      //
    },

    /**
     * 아임포트 이용 결제시스템 호출 함수
     */
    checkPay() {
      /* 1. 가맹점 식별하기 */
      const { IMP } = window;
      IMP.init("imp00423345"); // test 가맹점을 넣어놨어요

      //
      /* 2. 결제 데이터 정의하기 */
      const data = {
        pg: "html5_inicis", // PG사
        pay_method: "card", // 결제수단
        merchant_uid: `mid_${new Date().getTime()}`, // 주문번호
        amount: 10, // 결제금액
        name: "Unpeu 선물 구매", // 주문명
        buyer_name: "Unpeu Guest", // 구매자 이름
        buyer_tel: "01012341234", // 구매자 전화번호
        buyer_email: "example@example.com", // 구매자 이메일
        buyer_addr: "Unpeu", // 구매자 주소
        buyer_postcode: "06018", // 구매자 우편번호
      };

      /* 4. 결제 창 호출하기 */
      IMP.request_pay(data, this.callback);
    },
    callback(response) {
      /* 3. 콜백 함수 정의하기 */
      const { success, error_msg } = response;
      if (success) {
        Alert.paymentSuccess(this);
        this.$router.push({ name: "eventRoom" });
      } else {
        console.log(`결제 실패: ${error_msg}`);
        Alert.paymentFailure(this);
      }
    },
  },
  /**
   * 선택된 카드 색상을 바꿔주는 함수
   * 현재 노란색으로 지정해놓았으며, 나중에 색깔을 통일할 예정(style : .selectedCard 참고)
   */
  changeCardColor(reverse) {
    if (reverse) {
      document
        .getElementById("rootCards")
        .children[this.currentIdx].children[0].classList.add("selectedCard");
      document
        .getElementById("rootCards")
        .children[this.currentIdx].children[0].classList.remove("card");
    } else {
      document
        .getElementById("rootCards")
        .children[this.currentIdx].children[0].classList.remove("selectedCard");
      document
        .getElementById("rootCards")
        .children[this.currentIdx].children[0].classList.add("card");
    }
  },
};
</script>

<style>
h2 {
  border-bottom: none !important;
}
.message-title {
  text-align: center;
  font-size: 20px;
}
</style>
