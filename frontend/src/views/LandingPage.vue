<template>
    <v-container>
        <div class="landing"> 
            <span style = "font-size:2em; font-weight: bold ;">
              당신을 위한 조그마한 기록 그리고 선물<br/> Un Peu : 앙뿌<br/><br/>
             </span>
            <img width="250px" src="@/assets/main_logo4.gif"/><br><br/>
            <div class=description>친구에게 받은 URL이 있다면 여기로</div>
            <v-btn class=landing_btn elevation="2" width="210px" height="35px"  style = "font-size:1.2em; font-weight: bold;" @click="goToUrl"> URL로 접속하기 </v-btn><br><br>
            <div class=description >소셜로그인으로 간편하게</div>
            <router-link :to="{ name: 'Login' }">
            <v-btn class=landing_btn elevation="2" width="210px" height="35px" style = "font-size:1.2em; font-weight: bold;" > 방명록 만들러 가기 </v-btn>
            </router-link>
        </div>
        <router-view/>
    </v-container>
</template>

<script>
import { FRONT_URL} from "@/config/index";
export default {
  name: 'LandingPage',
  created() {
   },

  components: {
  },

  methods: {
    async goToUrl() {
      const { value: email } = await this.$swal.fire({
        title: '링크를 붙여넣어 주세요',
        input: 'text',//url
        inputPlaceholder: 'paste the link',
        inputValidator:(value) => {
              if (value.substr(0,FRONT_URL.length)!=FRONT_URL) {
                return '앙뿌의 링크를 입력해주세요!'
              }
            }
        })

        if (email) {
            location.replace(email)
        }
        
    }
  }
};

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




