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
        <v-tooltip left color="yellow lighten-3">
          <template v-slot:activator="{ on, attrs }">
            <div style="text-align:right">
              <v-icon color="primary"
              v-bind="attrs"
              v-on="on"
              >
              mdi-eye 
              </v-icon>
            </div>
          </template>
          <v-img v-model="picture">
          </v-img>
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

export default {
  components: { PresentRegister, PresentListSearch },
};
</script>

<style>
.present-mng-title {
  text-align: center;
  font-size: 20px;
}
</style>