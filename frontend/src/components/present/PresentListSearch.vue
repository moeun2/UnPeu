<template>
<div class="present-carousel">
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
  </div>
</template>

<script>
import {API_BASE_URL} from "../../config/index.js";
import {mapActions, mapState} from "vuex";
const presentStore="presentStore";
export default {
  data:()=>({
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
    ...mapActions(presentStore,["searchList"]),
    search(){
      this.searchList(1);
      this.cardList=this.presentList.Present;
      console.log(this.cardList);
      this.cardListCount=Math.ceil(this.cardList.length/3);
    }
  }
}
</script>

<style>
.card {
  margin: 5px;
}

.present-carousel{
  padding-top:5px;
}
.present-carousel .v-window__next {
        background:rgba(0,0,0,0.15);
        height:35px;
        width:35px;
    }
.present-carousel .v-window__prev {
        background:rgba(0,0,0,0.15);
        height:35px;
        width:35px;
        top:calc(50% - 20px);
    }
</style>