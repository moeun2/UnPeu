<template>
  <v-container fluid>
    <v-card>
      <v-card-title>
        <span>View</span>
      </v-card-title>

      <v-card-subtitle>
        <span>{{ form.category }}</span>
        <!-- 생성날짜 왼쪽 정렬 -->
        <span>{{ form.createdAt }}</span>
      </v-card-subtitle>

      <v-card-text>
        <v-text-field
          v-model="form.title"
          label="Title"
          readonly
        ></v-text-field>
        <!-- CKEditor로 수정 예정 -->
        <v-textarea v-model="form.contents" label="Content" readonly>
        </v-textarea>
      </v-card-text>

      <v-card-actions>
        <!-- button의 css와 간격, 위치 조정 필요 -->
        <!-- edit와 delete는 작성자에게만 보인다 -->
        <v-btn text @click="editDiary">edit</v-btn>
        <v-btn text @click="deleteDiary">delete</v-btn>
        <v-btn text @click="backMovePage">back</v-btn>
      </v-card-actions>

      <v-card-text>
        <!-- 댓글 -->
        <span>Comment</span>
        <comment-write> </comment-write>
        <comment></comment>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import CommentWrite from "@/components/diary/comment/CommentWrite.vue";
import Comment from "@/components/diary/comment/Comment.vue";

export default {
  name: "BoardDetail",

  data() {
    return {
      // form은 restApi를 통해서 Diary 객체를 가져온다
      form: {
        diaryId: 1,
        category: "2022_어른이날",
        title: "제목",
        contents:
          "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur sodales ligula in libero. Sed dignissim lacinia nunc.",
        createdAt: "2022-04-20",
      },
    };
  },

  components: {
    CommentWrite,
    Comment,
  },

  mounted() {
    // diaryId에 해당하는 Diary 객체 가져오기
    console.log(this.$route.params.diaryId);
  },

  methods: {
    editDiary() {
      console.log("edit");
      this.$router.push({
        name: "BoardEdit",
        params: { diaryId: this.form.diaryId },
      });
    },
    deleteDiary() {
      console.log("delete");
      this.$router.push({ name: "BoardList" });
    },
    backMovePage() {
      console.log("back");
      this.$router.push({ name: "BoardList" });
    },
  },
};
</script>

<style lang="scss" scoped>
</style>