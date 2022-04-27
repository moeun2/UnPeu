<template>
  <div>
      <div class="message-title">콩주머니 보내기</div>
      <present-select-list-search @present="getPresent"/>
      <present-message @message="getMessage"/>
  </div>
</template>

<script>
import PresentSelectListSearch from './present/PresentSelectListSearch.vue'
import PresentMessage from './present/PresentMessage.vue'
import {mapActions} from "vuex";
const presentStore="presentStore";
export default {
  components: { PresentSelectListSearch, PresentMessage },
  data() {
    return {
      message:{
        selectedPresentId :'',
        selectedPresentPrice :'',
        sender:'',
        content:''
      }
    }
  },
  methods : {
    ...mapActions(presentStore,["sendPresentMessage"]),
    getPresent(data){
      alert(data)
      this.message.selectedPresentId= data.selectedPresentId;
      this.message.selectedPresentPrice = data.selectedPresentPrice;
    },
    getMessage(data){
      alert(data)
      alert(data.sender + "\n" + data.content);
      this.message.sender = data.sender;
      this.message.content = data.content;
      if(this.message.sender != null && this.message.content != null){
         // this.checkPay();
         this.sendPresentMessage(this.message);
        this.$router.push({ name: "eventRoom" });
      }
      else{
        if(this.message.sender == null){
          alert("nickname을 써주세요")
        }else if(this.message.content == null){
          alert("메세지를 적어주세요")
        }
      }
     
    },
    
    /**
     * 아임포트 이용 결제시스템 호출 함수
     */
    checkPay() {
       /* 1. 가맹점 식별하기 */
      const { IMP } = window;
      IMP.init('imp00423345'); // test 가맹점을 넣어놨어요

      //
      /* 2. 결제 데이터 정의하기 */
      const data = {
        pg: 'html5_inicis',                           // PG사
        pay_method: 'card',                           // 결제수단
        merchant_uid: `mid_${new Date().getTime()}`,   // 주문번호
        amount:10,                                 // 결제금액
        name: 'Unpeu 선물 구매',                  // 주문명
        buyer_name: 'Unpeu Guest',                           // 구매자 이름
        buyer_tel: '01012341234',                     // 구매자 전화번호
        buyer_email: 'example@example.com',               // 구매자 이메일
        buyer_addr: 'Unpeu',                    // 구매자 주소
        buyer_postcode: '06018',                      // 구매자 우편번호
      };

      /* 4. 결제 창 호출하기 */
      IMP.request_pay(data, this.callback);
    },
    callback(response) {
      /* 3. 콜백 함수 정의하기 */
      const {
        success,
        error_msg,
        
      } = response;

      if (success) {
        alert('결제 성공');
        
      } else {
        alert(`결제 실패: ${error_msg}`);
        
        
      }
    },
  }
}
</script>

<style>
.message-title{
    text-align: center;
    font-size: 20px;
}
</style>