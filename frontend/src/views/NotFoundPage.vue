<template>
  <div class="view">
    <v-container>
      <div class="landing">
        <span style="font-size: 2em; font-weight: bold">
          올바르지 못한 경로입니다.<br /><br />
        </span>
        <img width="250px" src="@/assets/img/error-page.png" /><br /><br />
        <div class="description">여기를 눌러 앙뿌로 돌아가주세요</div>
        <router-link :to="{ name: 'Landing' }">
          <v-btn
            class="landing_btn"
            elevation="2"
            width="210px"
            height="35px"
            style="font-size: 1.2em; font-weight: bold"
          >
            앙뿌로 돌아가기
          </v-btn>
        </router-link>
      </div>
      <router-view />
    </v-container>
  </div>
</template>
<style scoped>
.landing {
  margin-top: 10vh;
  text-align: center;
  justify-content: center;
  font-family: "GangwonEdu_OTFBoldA"
}
.view {
  background-image: url("https://i.imgur.com/EI9xcZH.png");
  background-size: cover;
  height: 100vh;
  display: flex;
  align-items: center;
}
.landing_btn {
  background-color: #fff6ca !important;
}
</style>
