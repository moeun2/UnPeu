<template>
    <div>
        <h1>GoogleLogin</h1>
    </div>
</template>

<script>
import {googleLogin} from '@/api/user.js';

export default {
  name: "GoogleLogin",
  created() { 
    if(this.searchParam('code')){
        console.log(this.searchParam('code'))
        alert(this.searchParam('code'))
        //axios 로그인 진행 
        googleLogin(this.searchParam('code'),
        (res) => { 
            localStorage.setItem("accessToken",res.data.accessToken)
        },
        ()=>{
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