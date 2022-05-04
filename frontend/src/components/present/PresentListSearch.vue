<template>
<div class="present-carousel">
  <template v-if="cardList.length===0">
    <v-img
    src="@/assets/img/none_present.png"
    >
    </v-img>
  </template>
  <template v-else>
  <v-carousel hide-delimiters style="height:400px">
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
                    <p>가격 : {{cardList[+index+i].presentPrice}}</p>
                    <p>현재 받은 금액 : {{cardList[+index+i].receivedPrice}}</p>
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
  </template>
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
  data(){
    return{
    cardList: [],
    selectedCardList: null,
    present:{
      presentName:"",
      presentImg:"",
    },
    cardIndex:0,
    dialog:false,
    API_BASE_URL: API_BASE_URL,
    userId:this.$store.state.userStore.user.id,
    }
  },
  mounted(){
    this.search();
  },
  watch:{
    ...mapState(presentStore,["presentList"]),
    presentList: {
    deep: true,
    handler(newVal) {
      if(this.cardList.length === newVal.Present.length){
        this.cardList=newVal.Present;
      }else if(this.cardList.length < newVal.Present.length){
        this.cardList.push(newVal.Present[this.cardList.length]);
      }else{
        return;
      } 
    }
  }
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
      this.searchList(this.userId);
      this.cardList=this.presentList.Present;
      console.log(this.userId);
    },
    openDialog(index){
      this.dialog=true;
      this.selectedCardList=this.cardList[index];
    },
    closeDialog(){
      this.dialog=false;
    },
    remove(index){
      this.$swal.fire({
        title: '정말 삭제하시나요?',
        text: "다시 한번 생각해주세요!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: '네, 삭제합니다!'
      }).then((result) => {
        if (result.isConfirmed) {
          let rmv=this.deletePresent(this.cardList[index].presentId);
          if(rmv){
            this.$swal.fire(
              '삭제 성공!',
              '파일이 삭제되었습니다.',
              'success'
            )
            this.$delete(this.cardList,index);
          }else{
            this.$swal.fire(
              '삭제 실패!',
              '파일이 정상적으로 삭제되지 않았습니다.',
              'error'
            )
          }
        }
      })
      //
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