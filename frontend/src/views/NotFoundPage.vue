<template>
    <v-container>
        <div class="landing"> 
            <span style = "font-size:2em; font-weight: bold ;">
              올바르지 못한 경로입니다.<br/><br/>
             </span>
            <img width="250px" src="@/assets/img/error-page.png"/><br><br/>
            <div class=description >여기를 눌러 앙뿌로 돌아가주세요</div>
            <router-link :to="{ name: 'Landing' }">
            <v-btn class=landing_btn elevation="2" width="210px" height="35px" style = "font-size:1.2em; font-weight: bold;" > 앙뿌로 돌아가기 </v-btn>
            </router-link>
        </div>
        <router-view/>
    </v-container>
</template>