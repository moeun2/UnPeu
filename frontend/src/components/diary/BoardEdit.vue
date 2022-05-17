<template>
  <v-container class="pa-0">
    <board-write type="edit" :value="boardInfo"></board-write>
  </v-container>
</template>

<script>
import BoardWrite from "@/components/diary/BoardWrite.vue";
import { mapGetters, mapActions } from "vuex";
const diaryStore = "diaryStore";

export default {
  name: "BoardEdit",
  data() {
    return {
    };
  },

  components: {
    BoardWrite,
  },

  created() {
    this.AC_BOARD_DETAIL(this.$route.params.boardId);
  },

  computed: {
    // 이름 지정해서 getters 가져오기
    ...mapGetters(diaryStore, {
      boardInfo: "GET_BOARD_DETAIL",
    }),
  },

  methods: {
    ...mapActions(diaryStore, ["AC_BOARD_DETAIL"]),
  },
};
</script>

<style lang="scss" scoped>
</style>