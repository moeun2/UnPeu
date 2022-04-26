<template>
<div class="present-carousel">
  <v-carousel hide-delimiters style="height:350px">
    <v-carousel-item v-for="i in 3" :key="i">
      <v-layout row>
        <v-flex sm4 v-for="j in 3" :key="j" pl-2 pr-2 >
          <v-card class="card" @click="showModal=true">
            <v-img
              src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
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
  <v-btn @click="testEmit">test!!!!!!!!!!!</v-btn>
  <PresentPayModal v-if="showModal"  @close="showModal = false"></PresentPayModal>
  </div>
</template>

<script>
import PresentPayModal from "@/components/present/PresentPayModal";
export default {
components: {
    PresentPayModal,
  },
data() {
    return {
      showModal: false,
    }
},
methods:{
  testEmit(){
    this.$emit("test", 3);
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