<template>
<div class="present-carousel">
  <v-carousel hide-delimiters style="height:350px">
    <template v-for="(card, index) in cardList">
    <v-carousel-item v-if="(index + 1) % columns ===1 || columns === 1" :key="index">
      <v-row class="flex-nowrap">
          <template v-for="(n,i) in columns">
            <template v-if="(+index+i-1)<=cardList.length">
              <v-col :key="i">
                <v-hover v-slot="{ hover }">
                <v-card :elevation="hover ? 12 : 2" :class="{ 'on-hover' : hover }"  v-if="(+index+i)<cardList.length" class="card">
                  <v-img
                    :src="API_BASE_URL+cardList[+index+i].presentImg"
                    aspect-ratio="0.8"
                  >
                    <v-expand-transition>
                      <div
                        v-if="hover"
                        class="d-flex transition-fast-in-fast-out light-blue lighten-3 v-card--reveal display-3 white--text" style="height: 100%;"
                      >
                        <div class="align-self-center">
                          <v-btn
                            class="mx-2"
                            fab
                            color="yellow lighten-3"
                            @click="openDialog(+index+i)"
                            >
                            <v-icon dark>
                                mdi-pencil
                            </v-icon>
                          </v-btn>
                          <v-btn
                            class="mx-2"
                            fab
                            color="red lighten-3"  
                            @click="remove(+index+i)"
                          >
                            <v-icon dark>
                              mdi-close
                            </v-icon>
                          </v-btn>
                        </div>
                      </div>
                    </v-expand-transition>
                  </v-img>
                  <v-card-title primary-title>
                    {{ cardList[+index+i].presentName }}
                  </v-card-title>
                  <v-card-text>
                    가격 : {{cardList[+index+i].presentPrice}}
                  </v-card-text>
                  
                </v-card>
                </v-hover>
              </v-col>
            </template>
          </template>
        </v-row>
      </v-carousel-item>
    </template>
  </v-carousel>
  <present-update-modal :openDialog="dialog" :present="selectedCardList" @close="closeDialog" />
  </div>
</template>

<script>
import PresentUpdateModal from "@/components/present/PresentUpdateModal.vue";
import {API_BASE_URL} from "../../config/index.js";
import {mapActions, mapState} from "vuex";
const presentStore="presentStore";
export default {
  components:{
    PresentUpdateModal,
  },
  data:()=>({
    cardList: [],
    selectedCardList: null,
    present:{
      presentName:"",
      presentImg:"",
    },
    cardIndex:0,
    dialog:false,
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
    },
  },
  methods:{
    ...mapActions(presentStore,["searchList","deletePresent"]),
    search(){
      this.searchList(1);
      this.cardList=this.presentList.Present;
      console.log(this.cardList);
    },
    openDialog(index){
      this.dialog=true;
      this.selectedCardList=this.cardList[index];
    },
    closeDialog(){
      this.dialog=false;
    },
    remove(index){
      this.deletePresent(this.cardList[index].presentId);
    }
  }
}
</script>

<style>
.card {
  margin: 5px;
  -webkit-transition: all .7s ease;
    transition: all .7s ease;
}

.card:hover{
  -webkit-transform: scale(1.05);
    transform: scale(1.05);
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

.v-card--reveal {
    align-items: center;
    bottom: 0;
    justify-content: center;
    opacity: .95;
    position: absolute;
    width: 100%;
}
</style>