<template>
  <v-app>
    <div class="view">
      <router-view />
    </div>
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

body {
  background-image: url("https://i.imgur.com/EI9xcZH.png");
  background-size: cover;
}
/** 공통 색상 표 지정 **/
:root{
  --green-color : #ddefa5;
  --cancel-color : #FF7878;
  --confirm-color: #4CAF50;
  --close-color : #9e9e9e; /*vuetify grey */
}
</style>
