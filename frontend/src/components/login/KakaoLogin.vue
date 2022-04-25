<template>
    <div>
        <h1>kakaoLogin</h1>
    </div>
</template>

<script>
import {loginUser} from '@/api/user.js';

export default {
  name: "KakaoLogin",
  created() { 
    if(this.searchParam('code')){
        console.log(this.searchParam('code'))
        //axios 로그인 진행 
        loginUser(this.searchParam('code'),(res) => { 
            localStorage.setItem("accessToken",res.data.accessToken)
        },()=>{
            console.log("fail")
        });
      this.$router.push("/") //메인 페이지로 이동
    }
    },
  methods: {
        searchParam(key) {
            return new URLSearchParams(location.search).get(key);
        }
  }
    
}
</script>