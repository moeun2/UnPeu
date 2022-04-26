<template>
<div class="present-carousel">
  <v-carousel hide-delimiters style="height:350px">
    <v-carousel-item v-for="i in cardListCount" :key="i">
      <v-layout row id="cards">
        <v-flex  sm4 v-for="(card,idx) in cardList" :key="idx" pl-2 pr-2>
          <v-card  class="card" @click="checkModal(card,idx)">
            <v-img
              :src="API_BASE_URL+card.presentImg"
              aspect-ratio="0.8"
            ></v-img>
            <v-card-title primary-title >
              <div>
                <div> 가격 </div>
              </div>
            </v-card-title>
          </v-card>
        </v-flex>
      </v-layout>
    </v-carousel-item>
  </v-carousel>
  <v-btn @click="testEmit">test!!!!!!!!!!!</v-btn>
  <PresentPayModal v-if="showModal"  @close="showModal = false"></PresentPayModal>
  </div>
</template>

<script>
import {API_BASE_URL} from "@/config/index.js";
import {mapActions, mapState} from "vuex";
import PresentPayModal from "@/components/present/PresentPayModal";
const presentStore="presentStore";
export default {
  components: {
    PresentPayModal,
  },
  data:()=>({
    cardidx: 0,
    cardList: [],
    cardListCount: 0,
    API_BASE_URL: API_BASE_URL,
    showModal : false,
    selectedPresentId :'',
    selectedPresentPrice : '',
    
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
      this.searchList(1);
      this.cardList=this.presentList.Present;
      this.cardListCount=Math.ceil(this.cardList.length/3);
    },
    testEmit(){
    this.$emit("test", 3);
  },
  checkModal(card, idx){
    console.log(card)
    console.log(idx)
    this.selectedPresentId = card.presentId;
    this.selectedPresentPrice = 1000
    document.getElementById("cards").classList.remove('v-card--disabled');
    document.getElementById("cards").children[idx].classList.add('v-card--disabled');
    this.showModal = true;
    // document.getElementById("cards").children[idx].style.backgroundColor = "aquamarine !important";
    //document.getElementById("cards").children[2].classList.add('v-card--disabled');
    //document.getElementById("cards").children[2].classList.remove('v-card--disabled');
    //document.getElementById("cards").classList.add('v-card--disabled');

  }

  
  }
}
</script>

<style>
.card {
  margin: 5px;
  /* background-color:aquamarine !important */
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