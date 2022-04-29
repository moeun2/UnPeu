<template>
    <div>
        <h1>kakaoLogin</h1>
    </div>
</template>

<script>
import {kakaoLogin,getUserDetailUseToken} from '@/api/user.js';

export default {
  name: "KakaoLogin",
  created() { 
    if(this.searchParam('code')){
        console.log(this.searchParam('code'))
        //axios 로그인 진행 
        kakaoLogin(this.searchParam('code'),
        (res) => { 
            localStorage.setItem("accessToken",res.data.accessToken)
            getUserDetailUseToken(res.data.accessToken,(res)=>{
              console.log(res.data.User);
              this.$store.commit("userStore/setUser",res.data.User)//store에 user 저장
              this.$router.push({ name: 'eventRoom', params: {userid:this.$store.state.userStore.user.id}}) 
              // if(!res.data.User.userTitle){
              //   this.setUserEmailandBirth();
              // }
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

        // async setUserEmailandBirth(){
        //   const { value: data } = await this.$swal.fire({
        //     title: '회원 정보 입력',
        //     html:
        //       '<input id="swal-input1" class="swal2-input">' +
        //       '<input id="swal-input2" class="swal2-input">',
        //     focusConfirm: false,
        //     preConfirm: () => {
        //       return [
        //         document.getElementById('swal-input1').value,
        //         document.getElementById('swal-input2').value
        //       ]
        //     }
        //   })

        //   if (data) {
        //     this.$swal.fire(data)
        //     console.log(data)
        //   }
        // }
  }
    
}
</script>