<template>
  <div>
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
        v-model="message.nickname"
        solo
        label="닉네임"
        clearable
      ></v-text-field>
      <v-textarea
        v-model="message.content"
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
    선물을 <strong>선택</strong>할 시 결제 후 완료 메시지가 보내집니다<br/>
    <strong>현재, 선물 결제시 10원만 가결제되며, 이는 24시 이전에 취소됩니다.</strong>
    </v-alert>
    <v-btn
      tile
      color="success"
      type="submit"
      @click="sendMessage"
    >
      <v-icon left>
        mdi-email-edit
      </v-icon>
      보내기
    </v-btn>
    
  </v-row>
  </div>
</template>

<script>

export default {
  data: () =>({
    message: {
      nickname: "",
      content: "",
    },
  }),
  methods:{
    sendMessage(){
      this.$emit("message", {sender:this.message.nickname, content:this.message.content});
      // this.sendPresentMessage(this.message);
    },
  }
}
</script>

<style>

</style>