function paymentSuccess(vm){
    vm.$swal.fire(
        "결제 성공!",
        "결제를 무사히 성공했어요! 24시간 이내로 결제는 취소됩니다!",
        "success"
      );
}
function paymentFailure(vm){
    vm.$swal.fire(
        "결제 실패",
        "결제를 실패했어요ㅜㅜ 다시 한 번 시도해주세요",
        "error"
      );
}
function paymentCancel(vm){
    vm.$swal.fire(
        "Cancelled",
        "선물을 취소합니다. 다시 메세지 보내기를 눌러주세요 :)",
        "error"
      );
}
function sendMessageFailure(vm){
    vm.$swal.fire(
        "메세지 등록 실패",
        "메세지 등록을 실패했어요. 다시 메세지 보내기를 눌러주세요 :)",
        "error"
      );
}
function sendMessageSuccess(vm){
    vm.$swal.fire(
        "메세지 등록 성공",
        "메세지 등록을 성공했어요. 조그마한 마음을 같이 나눠보아요 :)",
        "success"
      );
}
const notSelectPresentBody ={
    title: "선물을 선택하지 않으셨어요!",
    text: "메세지만 보내시겠어요?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "선물도 보낼래요!",
    cancelButtonText: "메세지만 보내겠어요!",
    reverseButtons: false,
  }
const agreePaymentBody = {
    title: "선물결제를 동의하시나요?",
          text: "선물결제시 10원이 가결제되며, 24시간 전에 결제는 취소됩니다",
          icon: "warning",
          showCancelButton: true,
          confirmButtonText: "결제할래요!",
          cancelButtonText: "메세지만 보내겠어요!",
          reverseButtons: false,
}


export{
    paymentSuccess,
    paymentFailure,
    notSelectPresentBody,
    agreePaymentBody,
    paymentCancel,
    sendMessageFailure,
    sendMessageSuccess
}