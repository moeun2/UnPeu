<template>
  <v-container fluid>
    <v-card>
      <v-card-title>
        <span>View</span>
      </v-card-title>

      <v-card-subtitle>
        <span>{{ boardInfo.category }}</span>
        <span class="float-right">{{ boardInfo.createdAt }}</span>
      </v-card-subtitle>

      <v-card-text>
        <v-text-field
          v-model="boardInfo.title"
          label="Title"
          readonly
        ></v-text-field>
        <!-- CKEditor로 수정 예정 -->
        <v-textarea v-model="boardInfo.content" label="Content" readonly>
        </v-textarea>
        <div class="float-right">
          <v-btn text @click="editDiary">edit</v-btn>
          <!-- edit와 delete는 작성자에게만 보인다 -->
          <v-btn text @click="deleteDiary">delete</v-btn>
          <v-btn text @click="backMovePage">back</v-btn>
        </div>
      </v-card-text>

      <v-card-text>
        <!-- 댓글 -->
        <span>Comment</span>
        <comment-write> </comment-write>
        <comment
        :commentList="boardInfo.commentList"
        ></comment>
                <!-- @edit-comment="onEditComment" -->
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import CommentWrite from "@/components/diary/comment/CommentWrite.vue";
import Comment from "@/components/diary/comment/Comment.vue";
import { mapGetters, mapActions } from "vuex";
const diaryStore = "diaryStore";

export default {
  name: "BoardDetail",

  data() {
    return {};
  },

  components: {
    CommentWrite,
    Comment,
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
    ...mapActions(diaryStore, ["AC_BOARD_DETAIL", "AC_DELETE_BOARD"]),

    /* 편집하기 */
    editDiary() {
      console.log("edit");
      this.$router.push({
        name: "BoardEdit",
        params: { boardId: this.boardInfo.boardId },
      });
    },

    // onEditComment(commentInfo) {
      
    // },

    /* 삭제하기 */
    deleteDiary() {
      console.log("delete");
      if(confirm("정말로 삭제하시겠습니까?")) {
        this.AC_DELETE_BOARD(this.boardInfo.boardId);
        this.$router.push({ name: "BoardList" });
      }
    },

    /* 뒤로가기 */
    backMovePage() {
      console.log("back");
      this.$router.push({ name: "BoardList" });
    },
  },
};
</script>

<style lang="scss" scoped>
</style>