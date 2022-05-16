<template>
  <v-app>
    <div class="view">
      <router-view />
    </div>
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
  font-family: "GangwonEdu_OTFBoldA";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/GangwonEdu_OTFBoldA.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
/** 공통 색상 표 지정 **/
:root {
  --yellow-color: #ffd232;
  --pink-color: #ff85b3;
  --green-color: #dce775;
  --cancel-color: #ff7878;
  --confirm-color: #4caf50;
  --close-color: #9e9e9e; /*vuetify grey */
  --soft-blue-color: #b9e2fa; /*tab color */
  --speech-bubble-color: #85b9eaef; /**speech Bubble Color */

  --birthday-background: url("https://user-images.githubusercontent.com/40678696/167977803-08d49118-a649-478f-9980-732f1724df1a.png");
  --brithday-main-close: url("https://i.ibb.co/svNJKYZ/birthday-1.png");
  --brithday-main-open: url("https://i.ibb.co/sRswnrW/birthday-2.png");
  --birthday-pocket: url("https://i.ibb.co/3T021R4/pocket.png");
  --birthday-color: #ff85b3;

  --children-background: url("https://i.imgur.com/EI9xcZH.png");
  --children-main-close: url("https://i.imgur.com/TyYsFKh.jpg");
  --children-main-open: url("https://i.imgur.com/yBs2YNe.jpg");
  --children-pocket: url("https://i.ibb.co/Z26RYnw/pocket.png");
  --children-color: #ffd232;

  --default-background: url("https://i.ibb.co/gS1SGcP/EI9xcZH.png");
  --default-main-close: url("https://i.ibb.co/G5sJTbF/default-1.png");
  --default-main-open: url("https://i.ibb.co/Y3bKFMs/default-2.png");
  --default-pocket: url("https://i.ibb.co/m8QySwB/default-pocket.png");
  --default-color: #002060;
}

:root[color-theme="default-close"] {
  --background: var(--default-background);
  --profile: var(--default-color);
  --main: var(--default-main-close);
  --pocket: var(--default-pocket);
}
:root[color-theme="default-open"] {
  --background: var(--default-background);
  --profile: var(--default-color);
  --main: var(--default-main-open);
  --pocket: var(--default-pocket);
}
:root[color-theme="birthday-close"] {
  --background: var(--birthday-background);
  --profile: var(--birthday-color);
  --main: var(--brithday-main-close);
  --pocket: var(--birthday-pocket);
}
:root[color-theme="birthday-open"] {
  --background: var(--birthday-background);
  --profile: var(--birthday-color);
  --main: var(--brithday-main-open);
  --pocket: var(--birthday-pocket);
}
:root[color-theme="children-close"] {
  --background: var(--children-background);
  --profile: var(--children-color);
  --main: var(--children-main-close);
  --pocket: var(--children-pocket);
}
:root[color-theme="children-open"] {
  --background: var(--children-background);
  --profile: var(--children-color);
  --main: var(--children-main-open);
  --pocket: var(--children-pocket);
}
:root[color-theme="default-email"] {
  --profile: var(--children-color);
}
</style>
