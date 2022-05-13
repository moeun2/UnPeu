<template>
  <v-app>
    <div class="view">
      <router-view />
    </div>
    <input class="check" type="checkbox">
    <div class ="theme"></div>
    <email-fab></email-fab>
  </v-app>
</template>

<script>
import EmailFab from "./components/option/EmailFab.vue";

export default {
  components: { EmailFab },
  name: "App",

};
</script>

<style>
@import url("@/assets/css/reset.css");
@import url("@/assets/css/style.css");

* {
  font-family: "GangwonEdu_OTFBoldA" !important;
}
@font-face {
    font-family: 'GangwonEdu_OTFBoldA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/GangwonEdu_OTFBoldA.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

/* body {
  background-image: url("https://user-images.githubusercontent.com/40678696/167977803-08d49118-a649-478f-9980-732f1724df1a.png");
  background-size: cover;
} */
/** 공통 색상 표 지정 **/
:root{
  --yellow-color : #FFD232;
  --pink-color : #ff85b3;
  --green-color : #DCE775;
  --cancel-color : #FF7878;
  --confirm-color: #4CAF50;
  --close-color : #9e9e9e; /*vuetify grey */
  --soft-blue-color: #b9e2fa; /*tab color */
  --speech-bubble-color: #85b9eaef; /**speech Bubble Color */
  --test :  url("https://i.imgur.com/EI9xcZH.png");
  --test2 : url("https://user-images.githubusercontent.com/40678696/167977803-08d49118-a649-478f-9980-732f1724df1a.png");
}

</style>
