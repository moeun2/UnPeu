<template>
  <v-container>
    <div class="landing">
      <span style="font-size: 2em; font-weight: bold">
        당신을 위한 조그마한 기록 그리고 선물<br />
        Un Peu : 앙뿌<br /><br />
      </span>
      <img width="250px" src="@/assets/main_logo4.gif" /><br /><br /><br />
      <img
        class="google_login_btn"
        src="@/assets/btn_google_signin_light_normal_web@2x.png"
        v-on:click="GoogleLogin"
      /><br />
      <div id="my-signin2" style="display: none"></div>
      <img
        class="kakao_login_btn"
        src="@/assets/kakao_login_large_narrow.png"
        v-on:click="KakaoLogin"
      /><br />
    </div>
    <router-view />
  </v-container>
</template>

<script>
import { getUserDetailUseToken } from "@/api/user.js";
import { FRONT_URL } from "@/config/index";
export default {
  name: "LoginPage",
  created() {
    if (window.localStorage.getItem("accessToken")) {
      getUserDetailUseToken(
        window.localStorage.getItem("accessToken"),
        (res) => {
          // console.log(res.data.User);
          this.$store.commit("userStore/setUser", res.data.User); //store에 user 저장
          this.$router.push({
            name: "eventRoom",
            params: { userid: this.$store.state.userStore.user.id },
          });
        },
        () => {
          // console.log("getUserDetailUseToken fail")
          window.localStorage.removeItem("accessToken");
          this.$router.go;
        }
      );
    }
  },

  components: {},

  methods: {
    GoogleLogin() {
      window.location.replace(
        "https://accounts.google.com/o/oauth2/v2/auth?scope=email%20profile%20openid&response_type=code&client_id=530350751299-fbiks9onutpnvmgebr0fc5uvllj5fidn.apps.googleusercontent.com&redirect_uri=" +
          FRONT_URL +
          "/login/google"
      );
    },

    KakaoLogin() {
      window.location.replace(
        "https://kauth.kakao.com/oauth/authorize?client_id=c0ad1801cdf80282754cf18e79556743&redirect_uri=" +
          FRONT_URL +
          "/login/kakao&response_type=code"
      );
    },

    searchParam(key) {
      return new URLSearchParams(location.search).get(key);
    },
  },
};
</script>

<style lang="css">
@import url("@/assets/css/reset.css");
@import url("@/assets/css/style.css");

body {
  background-image: url("https://i.imgur.com/EI9xcZH.png");
  background-size: cover;
}

.test {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

#google_login_btn {
  width: 200px;
  height: 40px;
  background-color: #ffffff;
  border: 1px #a8a8a8 solid;
  color: black;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.kakao_login_btn {
  width: 195px;
  height: 40px;
  cursor: pointer;
  object-fit: cover;
  object-position: center;
  box-shadow: 1px 1px 1px rgb(226, 225, 225);
  margin-top: 10px;
}
.google_login_btn {
  width: 200px;
  height: 40px;
  cursor: pointer;
  object-fit: cover;
  margin-top: 10px;
}
.landing {
  text-align: center;
  justify-content: center;
}
</style>