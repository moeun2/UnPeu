<template>
  <div>
    <v-icon>mdi-visiblity</v-icon>
    <v-row
    justify="end"
    >
      <v-col cols="11">
        <div class="present-mng-title">선물 관리하기</div>
      </v-col>
      <v-col cols="1">
        <v-tooltip left color="blue lighten-3">
          <template v-slot:activator="{ on, attrs }">
            <div style="text-align:right">
              <v-icon id="present-icon"
              v-bind="attrs"
              v-on="on"
              >
              mdi-eye 
              </v-icon>
            </div>
          </template>
          <template v-if="present == null">
            <span> 아무 것도 구매할 수 없습니다.</span>
          </template>
          <template v-else>
            <p>
              현재 구매 가능한 선물
            </p>
            <div style="text-align:center; margin: 10px;">
            <v-img max-width="300px" max-height="200px" :src="API_BASE_URL+present.presentImg">
            </v-img>
            </div>
            <span>현재 모은 금액으로 {{present.presentName}}을 구매 가능합니다</span>
          </template>
        </v-tooltip>
      </v-col>
    </v-row>
    <present-register />
    <present-list-search />
  </div>
</template>

<script>
import PresentListSearch from "@/components/present/management/PresentListSearch.vue";
import PresentRegister from "@/components/present/management/PresentRegister.vue";
import { mapState } from "vuex";
import { API_BASE_URL } from "@/config/index.js";

const presentStore = "presentStore";
export default {
  components: { PresentRegister, PresentListSearch },
  data(){
    return{
      API_BASE_URL:API_BASE_URL,
      present: null,
    }
  },
  watch: {
    appropriatePresent: {
      deep: true,
      handler(newVal) {
        console.log(newVal);
        if(newVal==null) return;
        this.present=newVal;
        console.log(this.present);
      }
    },
  },
  computed:{
    ...mapState(presentStore, ["appropriatePresent"]),
  }
};
</script>

<style>
#present-icon{
 color: grey;
}

#present-icon:hover{
  color: rgb(53, 133, 204);
}

.present-mng-title {
  text-align: center;
  font-size: 20px;
}
</style>