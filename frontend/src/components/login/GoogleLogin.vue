<template>
  <div>
    <h1>GoogleLogin</h1>
  </div>
</template>

<script>
import { googleLogin, getUserIdUseToken, updateUserEmailBirth } from "@/api/user.js";
import flatpickr from "flatpickr";
import "flatpickr/dist/flatpickr.min.css";

export default {
  name: "GoogleLogin",

  created() {
    if (this.searchParam("code")) {
      //구글 로그인 진행
      googleLogin(
        this.searchParam("code"),
        (res) => {
          const token = res.data.accessToken;
          //로그인 성공시 token 으로 userid값 가져오기
          getUserIdUseToken(token, (res) => {
            if (!res.data.User.userEmail || !res.data.User.userBirth) {
              //userEmail이나 birth가 하나라도 없으면 회원가입 방금한 사람
              this.setUserBirth(token, res.data.User); //생일이랑 email 수정-> 수정 성공시 accesstoken+ user정보 저장하고 마이페이지 이동
            } else {
              //이전에 생일이랑 email 작성한사람 이면 바로 accesstoken이랑 user정보 저장 후 마이페이지 이동
              localStorage.setItem("accessToken", token);
              this.$store.commit("userStore/setUser", res.data.User);
              this.$router.push({
                name: "eventRoom",
                params: { userid: this.$store.state.userStore.user.id },
              });
            }
          });
        },
        () => {
          console.log("fail");
        }
      );
      this.$router.push({ name: "Login" });
    }
  },

  methods: {
    searchParam(key) {
      return new URLSearchParams(location.search).get(key);
    },

    async setUserBirth(accessToken, user) {
      //accessToken,user
      let flatpickrInstance;

      const { value: data } = await this.$swal.fire({
        title: "이메일과 생일을 입력해주세요!",
        html:
          '<div>이메일:<input input type="email" placeholder="이메일을 입력해주세요" id="email" class="swal2-input" ></div>' +
          '<div>생 일 :<input placeholder="생일을 입력해주세요" class="swal2-input" id="birth"></div><br/>'+
          '<label><input type="checkbox" id="isAgree" name="scales" checked>ㅤ생일과 이메일을 공개하는 것에 동의합니다.</label>',
        inputLabel:
          "여러분의 이메일과 생일을 입력해주세요. 드디어 마이페이지가 생성됩니다 :)",
        stopKeydownPropagation: false,
        focusConfirm: true,
        showCancelButton: true,
        
        preConfirm: () => {
          var exptext = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
          if (!flatpickrInstance.selectedDates[0]) {
            this.$swal.showValidationMessage(`생일을 입력해주세요`);
          }else if (flatpickrInstance.selectedDates[0] > new Date()) {
            this.$swal.showValidationMessage(`혹시.. 아직 안태어나셨나요? 생일을 올바르게 입력해주세요 :)`)
          }
          if (!document.getElementById("email").value||!exptext.test(document.getElementById("email").value)) {
            this.$swal.showValidationMessage(`이메일을 입력해주세요`);
          }
          return {
            userEmail: document.getElementById("email").value,
            userBirth: document.getElementById("birth").value,
            isAgree: document.getElementById("isAgree").checked
          };
        },

        willOpen: () => {
          // 달력 날짜입력 추가 flatpickr
          flatpickrInstance = flatpickr(
            this.$swal.getPopup().querySelector("#birth"),
            {
              allowInput:true
            }
          );
        },
      });

      if (data) {
        // console.log(data);
        //여기서 email, 생일 수정
        //axios 성공시에만 엑세스 토큰 저장/ vuex저장
        await updateUserEmailBirth(accessToken, data, (res) => {
          localStorage.setItem("accessToken", accessToken);
          this.$store.commit("userStore/setUser", user);
          this.$router.push({
            name: "eventRoom",
            params: { userid: res.data.User.id },
          });
        });
      }
    },
  },
};
</script>