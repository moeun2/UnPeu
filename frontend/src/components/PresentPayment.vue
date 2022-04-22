<template>
  <div>
      <div class="message-title">콩주머니 보내기</div>
      <present-list-search/>
      <present-message/>
  </div>
</template>

<script>
import PresentListSearch from './present/PresentListSearch.vue'
import PresentMessage from './present/PresentMessage.vue'
export default {
  components: { PresentListSearch, PresentMessage },

}
</script>

<style>
.message-title{
    text-align: center;
    font-size: 20px;
}
</style>