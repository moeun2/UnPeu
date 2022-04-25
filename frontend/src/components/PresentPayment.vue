<template>
  <div>
      <div class="message-title">콩주머니 보내기</div>
      <present-list-search/>
      <present-message/>
  </div>
</template>

<script>
import PresentListSearch from './present/PresentListSearch.vue'
import PresentMessage from './present/PresentMessage.vue'
export default {
  components: { PresentListSearch, PresentMessage },
  data() {
    return {
    }
  },
  methods : {
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
        amount:1000 ,                                 // 결제금액
        name: 'Unpeu 선물 구매',                  // 주문명
        buyer_name: '홍길동',                           // 구매자 이름
        buyer_tel: '01012341234',                     // 구매자 전화번호
        buyer_email: 'example@example.com',               // 구매자 이메일
        buyer_addr: '신사동 661-16',                    // 구매자 주소
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