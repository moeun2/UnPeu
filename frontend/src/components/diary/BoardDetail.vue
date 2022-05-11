<template>
  <v-container fluid>
    <v-card outlined>
      <v-card-subtitle>
        <span>{{ boardInfo.category }}</span>
        <span class="float-right">{{ boardInfo.createdAt }}</span>
      </v-card-subtitle>

      <v-card-title class="pt-0 pb-0 text-h4 text--primary">
        {{ boardInfo.title }}
      </v-card-title>

      <v-divider class="mx-6"></v-divider>
      <v-card-text>

        <div class="ql-container ql-snow yellow lighten-5">
          <div class= "ql-editor" v-html="boardInfo.content"></div>
        </div>
      </v-card-text>

      <v-divider class="mx-4"></v-divider>

      <v-card-title> Comment </v-card-title>

      <v-card-text>
        <comment-write> </comment-write>
        <comment :commentList="boardInfo.commentList"></comment>
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          v-if="loginUser && boardInfo.userId == loginUser.id"
          color="var(--confirm-color)"
          class="white--text"
          @click="editDiary"
          ><v-icon dark left>mdi-pencil</v-icon>edit</v-btn
        >
        <v-btn
          v-if="loginUser && boardInfo.userId == loginUser.id"
          color="var(--cancel-color)"
          class="white--text"
          @click="deleteDiary"
          ><v-icon dark left>mdi-delete</v-icon>delete</v-btn
        >
        <v-btn color="var(--close-color)" class="ma-2 white--text" @click="backMovePage"
          ><v-icon dark left>mdi-arrow-left</v-icon>back</v-btn
        >
      </v-card-actions>
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
    return {
      loginUser: this.$store.state.userStore.user,
    };
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
      // console.log("edit");
      this.$router.push({
        name: "BoardEdit",
        params: { boardId: this.boardInfo.boardId },
      });
    },

    /* 삭제하기 */
    deleteDiary() {
      // console.log("delete");
      this.$swal
        .fire({
          icon: "question",
          title: "Delete",
          html: "정말로 삭제하시겠습니까? ",
          showCancelButton: true,
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.AC_DELETE_BOARD(this.boardInfo.boardId);
            this.$router.push({ name: "BoardList" });
          }
        });
    },

    /* 뒤로가기 */
    backMovePage() {
      // console.log("back");
      this.$router.go(-1);
    },
  },
};
</script>

<style scoped>
@import url("@/assets/css/editor.css");
</style>