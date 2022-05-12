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
import EmailFab from "./components/EmailFab.vue";

export default {
  components: { EmailFab },
  name: "App",

};
</script>

<style>
@import url("@/assets/css/reset.css");
@import url("@/assets/css/style.css");

* {
  font-family: "hiffy" !important;
}
@font-face {
  font-family: "hiffy";
  src: url("assets/font/hiffy.ttf") format("truetype");
  font-weight: 400;
}

/* body {
  background-image: url("https://user-images.githubusercontent.com/40678696/167977803-08d49118-a649-478f-9980-732f1724df1a.png");
  background-size: cover;
} */
/** 공통 색상 표 지정 **/
:root{
  --green-color : #DCE775;
  --cancel-color : #FF7878;
  --confirm-color: #4CAF50;
  --close-color : #9e9e9e; /*vuetify grey */
  --soft-blue-color: #b9e2fa; /*tab color */
  --test :  url("https://i.imgur.com/EI9xcZH.png");
  --test2 : url("https://user-images.githubusercontent.com/40678696/167977803-08d49118-a649-478f-9980-732f1724df1a.png");
}

</style>
