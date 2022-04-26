<template>
  <div>
    <v-form @submit="sendMessage">
      <v-carousel hide-delimiters style="height:350px">
        <v-carousel-item v-for="i in cardListCount" :key="i">
          <v-layout row>
            <v-flex sm4 v-for="(card,idx) in cardList" :key="idx" pl-2 pr-2>
              <v-card class="card">
                <v-img
                  :src="API_BASE_URL+card.presentImg"
                  aspect-ratio="0.8"
                ></v-img>
                <v-card-title primary-title>
                  <div>
                    <div> 가격 </div>
                  </div>
                </v-card-title>
              </v-card>
            </v-flex>
          </v-layout>
        </v-carousel-item>
      </v-carousel>
      <v-text-field
        v-model="nickname"
        solo
        label="닉네임"
        clearable
      ></v-text-field>
      <v-textarea
        v-model="content"
        solo
        name="input-7-4"
        label="메시지 내용"
      ></v-textarea>
    <v-row
    justify="space-around"
  >
    <v-alert
      dense
      type="info"
    >
      선물을 <strong>선택</strong>할 시 결제 후 메시지가 보내집니다
    </v-alert>
    <v-btn
      tile
      color="success"
      type="submit"
    >
      <v-icon left>
        mdi-email-edit
      </v-icon>
      보내기
    </v-btn>
    
  </v-row>
    </v-form>
  </div>
</template>

<script>
import {mapActions} from "vuex";
const presentStore="presentStore";
export default {
  data: () =>({
    message: {
      nickname: null,
      content: null,
    },
  }),
  methods:{
    ...mapActions(presentStore,["sendPresentMessage"]),
    sendMessage(){
      //TODO : 결제창 띄우기
      this.sendPresentMessage(this.message);
    }
  }

}
</script>

<style>

</style>