<template>
  <div>
    <v-icon>mdi-visiblity</v-icon>
    <div class="present-mng-title">선물 관리하기</div>
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