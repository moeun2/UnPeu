<template>
    <v-container>
        <button></button>
        <div class="landing"> 
            <span style = "font-size:2em; font-weight: bold ;">
              당신을 위한 조그마한 기록 그리고 선물<br>Un Peu : 앙뿌<br>
             </span>
            <img width="450px" src="@/assets/moeun 2.png"/><br>
            <div class=description>친구에게 받은 URL이 있다면 여기로</div>
            <v-btn class=landing_btn elevation="2" width="210px" height="35px"  style = "font-size:1.2em; font-weight: bold;"> URL로 접속하기 </v-btn><br><br>
            <div class=description >소셜로그인으로 간편하게</div>
            <router-link :to="{ name: 'Login' }">
            <v-btn class=landing_btn elevation="2" width="210px" height="35px" style = "font-size:1.2em; font-weight: bold;" > 방명록 만들러 가기 </v-btn>
            </router-link>
        </div>
        <router-view/>
    </v-container>
</template>

<script>

</script>

<style>
.description{
    font-size:0.9em; 
    font-weight: bold ;
    color: rgba(0, 0, 0, 0.789);
}

.landing_btn{
    background-color:#fff6ca !important;
}

</style>




