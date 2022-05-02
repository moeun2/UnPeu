<template>
  <v-app>
    <div class="view">
      <!-- 같은 경로(다른 param)로 라우터 이동시 화면이 변경안되는 것 때문에 추가 -->
      <router-view/>
    </div>
    <email-fab></email-fab>
  </v-app>
</template>

<script>
import EmailFab from './components/EmailFab.vue';

export default {
  components: { EmailFab },
  name: 'App'
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
</style>
