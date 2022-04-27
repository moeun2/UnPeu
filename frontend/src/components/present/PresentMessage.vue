<template>
  <div>
    <v-carousel hide-delimiters style="height:350px">
      <template v-for="(card, index) in cardList">
        <v-carousel-item v-if="(index + 1) % columns ===1 || columns === 1" :key="index">
          <v-row class="flex-nowrap">
              <template v-for="(n,i) in columns">
                <template v-if="(+index+i-1)<=cardList.length">
                  <v-col :key="i">
                    <v-card v-if="(+index+i)<cardList.length" class="card">
                      <v-img
                        :src="API_BASE_URL+cardList[+index+i].presentImg"
                        aspect-ratio="0.8"
                      ></v-img>
                      <v-card-title primary-title>
                      <div>
                        <div> 가격 </div>
                      </div>
                    </v-card-title>
                  </v-card>
                </v-col>
              </template>
            </template>
          </v-row>
        </v-carousel-item>
      </template>
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
import {API_BASE_URL} from "../../config/index.js";
import {mapActions, mapState} from "vuex";
const presentStore="presentStore";
export default {
  data: () =>({
    nickname: null,
    content: null,
    cardList: [],
    API_BASE_URL: API_BASE_URL,
  }),
  mounted(){
    this.search();
  },
  computed:{
    ...mapState(presentStore,["presentList"]),
    columns(){
      // 카드 개수 3개로 고정
      return 3;
    }

  },
  methods:{
    ...mapActions(presentStore,["searchList", "sendPresentMessage"]),
    search(){
      this.searchList(1);
      this.cardList=this.presentList.Present;
      console.log(this.cardList);
      this.cardListCount=Math.ceil(this.cardList.length/3);
    },
    sendMessage(){
      //TODO : 결제창 띄우기
      this.$emit("message", {sender:this.message.nickname, contents:this.message.content});
      this.sendPresentMessage(this.message);
    },
  }
}
</script>

<style>

</style>