<template>
    <v-container>
        <div class="landing"> 
            <span style = "font-size:2em; font-weight: bold ;">
              당신을 위한 조그마한 기록 그리고 선물<br><br>Un Peu : 앙뿌<br><br>
             </span>
            <img width="450px" src="@/assets/moeun 2.png"/><br>
            <img class="google_login_btn" src="@/assets/btn_google_signin_light_normal_web@2x.png" v-on:click="GoogleLoginBtn"/><br>
            <div id="my-signin2" style="display: none"></div>
            <img class='kakao_login_btn' src="@/assets/kakao_login_large_narrow.png" v-on:click="KakaoLogin"/>
        </div>
        <div id="google_login_btn" v-on:click="KakaoLogout">logout</div>
        <router-view/>
    </v-container>
</template>

<script>
export default {
  name: 'LoginPage',

  components: {
  },

  methods: {
      GoogleLoginBtn:function(){
      var self = this;

      window.gapi.signin2.render('my-signin2', {
        scope: 'profile email',
        width: 240,
        height: 50,
        longtitle: true,
        theme: 'dark',
        onsuccess: this.GoogleLoginSuccess,
        onfailure: this.GoogleLoginFailure,
      });

      setTimeout(function () {
        if (!self.googleLoginCheck) {
          const auth = window.gapi.auth2.getAuthInstance();
          auth.isSignedIn.get();
          document.querySelector('.abcRioButton').click();
        }
      }, 1500)

    },
    async GoogleLoginSuccess(googleUser) {
      const googleEmail = googleUser.getBasicProfile().getEmail();
      if (googleEmail !== 'undefined') {
        console.log(googleEmail);
        this.$router.go();//새로고침
      }
    },
    //구글 로그인 콜백함수 (실패)
    GoogleLoginFailure(error) {
      console.log(error);
    },

    KakaoLogin(){
      window.location.replace(
        "https://kauth.kakao.com/oauth/authorize?client_id=c0ad1801cdf80282754cf18e79556743&redirect_uri=http://localhost:8081/login/kakao&response_type=code"
      );
    },
    KakaoLogout(){
      window.location.replace(
        "https://kauth.kakao.com/oauth/logout?client_id=c0ad1801cdf80282754cf18e79556743&logout_redirect_uri=http://localhost:8081/login/kakao"
      );
    },
    searchParam(key) {
      return new URLSearchParams(location.search).get(key);
    }
  }
};
</script>

<style lang="css">
@import url("@/assets/css/reset.css");
@import url("@/assets/css/style.css");


body {
  background-image: url("https://i.imgur.com/EI9xcZH.png");
  background-size: cover;
}

 .test{
    display:flex; 
    justify-content: center; 
    align-items: center; 
    height:100vh; }
  
  #google_login_btn{ 
    width: 200px; 
    height:40px; 
    background-color:#ffffff; 
    border:1px #a8a8a8 solid; 
    color:black; 
    display:flex; 
    align-items: center; 
    justify-content: center; 
    cursor:pointer; 
    }
  .google_login_btn{
    width: 200px; 
    height:40px; 
    cursor:pointer; 
    object-fit: cover;     
    object-position: center;
    margin-top: 10px;
    
  }
  .kakao_login_btn{
    width: 195px; 
    height:40px; 
    cursor:pointer; 
    object-fit: cover;     
    object-position: center;
    box-shadow : 1px 1px 1px  rgb(226, 225, 225);
    margin-top: 10px;
  }

  .landing{
    text-align: center;
    margin-top: 200px;
  }
</style>