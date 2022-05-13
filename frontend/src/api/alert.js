function paymentSuccess(vm) {
  vm.$swal.fire(
    "결제 성공!",
    "결제를 무사히 성공했어요! 24시간 이내로 결제는 취소됩니다!",
    "success"
  );
}
function paymentFailure(vm) {
  vm.$swal.fire(
    "결제 실패",
    "결제를 실패했어요ㅜㅜ 다시 한 번 시도해주세요",
    "error"
  );
}
function paymentCancel(vm) {
  vm.$swal.fire(
    "Cancelled",
    "선물을 취소합니다. 다시 메세지 보내기를 눌러주세요 :)",
    "error"
  );
}
function sendMessageFailure(vm) {
  vm.$swal.fire(
    "메세지 등록 실패",
    "메세지 등록을 실패했어요. 다시 메세지 보내기를 눌러주세요 :)",
    "error"
  );
}
function sendMessageSuccess(vm) {
  vm.$swal.fire(
    "메세지 등록 성공",
    "메세지 등록을 성공했어요. 조그마한 마음을 같이 나눠보아요 :)",
    "success"
  );
}
function sendFeedbackSuccess(vm) {
  vm.$swal.fire(
    "피드백 보내기 성공",
    "피드백 보내주셔서 감사해요! 당신은 천사얌 ଘ(੭ˊᵕˋ)੭* ੈ✩‧₊˚",
    "success"
  );
}
function sendFeedbackFailure(vm) {
  vm.$swal.fire(
    "피드백 보내기 실패",
    "다시 피드백 부탁드려요 천사님 ଘ(੭ˊᵕˋ)੭* ੈ✩‧₊˚",
    "error"
  );
}
function selectAmountFailure(vm) {
  vm.$swal.fire("가격 직접입력 실패", "100원 이상 금액을 입력해주세요˚", "error");
}
const notSelectPresentBody = {
  title: "선물을 선택하지 않으셨어요!",
  text: "메세지만 보내시겠어요?",
  icon: "warning",
  showCancelButton: true,
  confirmButtonText: "선물도 보낼래요!",
  cancelButtonText: "메세지만 보내겠어요!",
  reverseButtons: false,
};
const agreePaymentBody = {
  title: "선물결제를 동의하시나요?",
  text: "선물결제시 100원이 가결제되며, 24시간 전에 결제는 취소됩니다",
  icon: "warning",
  showCancelButton: true,
  confirmButtonText: "결제할래요!",
  cancelButtonText: "메세지만 보내겠어요!",
  reverseButtons: false,
};

/* EventRoom */
const resetMessageCheck = {
  title: "컨셉 초기화",
  text: "선물리스트와 메세지가 초기화됩니다. 실행시 돌아갈 수 없으니 신중히 선택하세요!",
  icon: "warning",
  showCancelButton: true,
  confirmButtonText: "할래요!",
  cancelButtonText: "안할래요!",
  reverseButtons: false,
};

function resetMessageCancel(vm) {
  vm.$swal.fire("Cancelled", "초기화를 취소합니다. ", "error");
}

function resetMessageFail(vm) {
  vm.$swal.fire("Oops...!", "삭제할 메세지가 없어요!", "error");
}

function resetMessageSuccess(vm) {
  vm.$swal.fire("초기화 성공!", "초기화가 완료되었습니다! ", "success");
}

const saveMessageCheck = {
  title: "메세지 저장",
  text: "메세지가 다이어리에 저장되고, 메세지와 선물리스트가 포함된 데이터는 자동으로 초기화됩니다.",
  icon: "warning",
  showCancelButton: true,
  confirmButtonText: "할래요!",
  cancelButtonText: "안할래요!",
  reverseButtons: false,
};

function saveMessageCancel(vm) {
  vm.$swal.fire("Cancelled", "메세지 저장을 취소합니다. ", "error");
}

function saveMessageFail(vm) {
  vm.$swal.fire("Oops...!", "저장할 메세지가 없어요!", "error");
}

function saveMessageSuccess(vm) {
  vm.$swal.fire(
    "메세지 저장 성공!",
    "메세지를 다이어리에서 확인하실 수 있습니다! ",
    "success"
  );
}

function setMonthAndDate(vm) {
  vm.$swal.fire(
    "컨셉 변경 완료!",
    "설정된 날짜는 0시 정각에 실행됩니다.",
    "success"
  );
}


export {
  selectAmountFailure,
  paymentSuccess,
  paymentFailure,
  notSelectPresentBody,
  agreePaymentBody,
  paymentCancel,
  sendMessageFailure,
  sendMessageSuccess,
  sendFeedbackSuccess,
  sendFeedbackFailure,
  resetMessageCheck,
  resetMessageCancel,
  resetMessageFail,
  resetMessageSuccess,
  saveMessageCheck,
  saveMessageCancel,
  saveMessageFail,
  saveMessageSuccess,
  setMonthAndDate,
};
