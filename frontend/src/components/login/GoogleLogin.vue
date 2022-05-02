<template>
    <div>
        <h1>GoogleLogin</h1>
    </div>
</template>

<script>
import {googleLogin,getUserIdUseToken,updateUserEmailBirth} from '@/api/user.js';
import flatpickr from "flatpickr";
import "flatpickr/dist/flatpickr.min.css";

export default {
  name: "GoogleLogin",
  created() { 
    if(this.searchParam('code')){
      //구글 로그인 진행 
        googleLogin(this.searchParam('code'),
        (res) => {
          const token=res.data.accessToken
          //로그인 성공시 token 으로 userid값 가져오기
            getUserIdUseToken(token,(res)=>{

              if(!res.data.User.userEmail&&!res.data.User.userBirth){//userEmail이 없으면 회원가입 방금한 사람
                this.setUserBirth(token,res.data.User)//생일이랑 email 수정-> 수정 성공시 accesstoken+ user정보 저장하고 마이페이지 이동 
                 
              }else{//이전에 생일이랑 email 작성한사람 이면 바로 accesstoken이랑 user정보 저장 후 마이페이지 이동
                localStorage.setItem("accessToken",token)
                this.$store.commit("userStore/setUser",res.data.User)
                this.$router.push({ name: 'eventRoom', params: {userid:this.$store.state.userStore.user.id}})
              }
            },)
        },
        ()=>{
            console.log("fail")
        });
      this.$router.push({ name: 'Login'})
    }
    },
  methods: {
        searchParam(key) {
            return new URLSearchParams(location.search).get(key);
        },

        async setUserBirth(accessToken,user){//accessToken,user
          let flatpickrInstance

          const { value: data }=await this.$swal.fire({
            title: '이메일과 생일을 입력해주세요!',
            html: '<input placeholder="이메일을 입력해주세요" id="swal-input1" class="swal2-input">' +
            '<input placeholder="생일을 입력해주세요" class="swal2-input" id="expiry-date">',
            stopKeydownPropagation: false,
            focusConfirm: true,
            showCancelButton: true,
            preConfirm: () => {//validation 설정
              if (!flatpickrInstance.selectedDates[0]) {
                this.$swal.showValidationMessage(`생일을 입력해주세요`)
              }
              if (!document.getElementById('swal-input1').value) {
                this.$swal.showValidationMessage(`이메일을 입력해주세요`)
              }
              return {
                userEmail:document.getElementById('swal-input1').value,
                userBirth:document.getElementById('expiry-date').value
                }
            },
            willOpen: () => {// 달력 날짜입력 추가 flatpickr
              flatpickrInstance = flatpickr(
                this.$swal.getPopup().querySelector('#expiry-date')
              )
            },
          })

          if(data){
            console.log(data)
            //여기서 email, 생일 수정
            //axios 성공시에만 엑세스 토큰 저장/ vuex저장
            await updateUserEmailBirth(accessToken,data,(res)=>{
              localStorage.setItem("accessToken",accessToken)
              this.$store.commit("userStore/setUser",user)
              this.$router.push({ name: 'eventRoom', params: {userid:res.data.User.id}})
            })
          }
          
          
        },

  }
    
}
</script>