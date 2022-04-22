<template>
  <v-container>
    <board-write type="edit" :value="form"></board-write>
  </v-container>
</template>

<script>
import BoardWrite from "@/components/diary/BoardWrite.vue";

export default {
  name: "BoardEdit",
  data() {
    return {
      form: {
        category: "2022_어른이날",
        title: "제목",
        contents:
          "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur sodales ligula in libero. Sed dignissim lacinia nunc.",
      },
    };
  },

  components: {
    BoardWrite,
  },

  mounted() {
    // diaryId에 해당하는 Diary 객체 가져오기
    // 가져온 값은 form에 저장
    console.log(this.$route.params.diaryId);
  },
};
</script>

<style lang="scss" scoped>
</style>