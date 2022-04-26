<template>
<div class="present-carousel">
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
  </div>
</template>

<script>
import {API_BASE_URL} from "../../config/index.js";
import {mapActions, mapState} from "vuex";
const presentStore="presentStore";
export default {
  data:()=>({
    cardidx: 0,
    cardList: [],
    cardListCount: 0,
    API_BASE_URL: API_BASE_URL,
  }),
  mounted(){
    this.search();
  },
  computed:{
    ...mapState(presentStore,["presentList"]),

  },
  methods:{
    ...mapActions(presentStore,["searchList"]),
    search(){
      this.searchList(0);
      this.cardList=this.presentList.Present;
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