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
        }
  }
    
}
</script>