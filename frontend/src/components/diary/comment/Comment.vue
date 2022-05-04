<template>
  <v-simple-table dense>
    <tbody v-if="commentList.length">
      <tr v-for="item in commentList" :key="item.commentId">
        <td style="width: 100px;" class="pa-0">
          <v-icon small>mdi-account</v-icon>{{ item.writer }}
        </td>

        <td class="content pa-0">{{ item.content }}</td>

        <td style="width: 110px;" class="pa-0 text-center">
          {{ item.createdAt }}
        </td>

        <td style="width: 23px;" class="pa-0">
          <v-btn @click="editComment(item)" icon x-small color="grey"
            ><v-icon>mdi-pencil</v-icon></v-btn
          >
        </td>

        <td style="width: 20px;" class="pa-0">
          <v-btn @click="deleteComment(item)" icon x-small color="red"
            ><v-icon>mdi-close</v-icon></v-btn
          >
        </td>
      </tr>
    </tbody>
  </v-simple-table>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
const diaryStore = "diaryStore";

export default {
  name: "CommentList",

  data() {
    return {};
  },

  props: {
    commentList: {
      type: Array,
    },
  },

  computed: {
    // 이름 지정해서 getters 가져오기
    ...mapGetters(diaryStore, {
      nonUser: "GET_COMMENT_DETAIL",
    }),
  },

  methods: {
    ...mapActions(diaryStore, [
      "ACT_EDIT_COMMENT",
      "ACT_DELETE_COMMENT",
      "ACT_SETTING_COMMENT",
    ]),

    /* 댓글 수정 요청하기 */
    editComment(commentInfo) {
      // console.log("edit comment");
      var input = prompt("비밀번호를 입력하세요");

      if (input == commentInfo.password) {
        this.ACT_SETTING_COMMENT({
          editFlag: true,
          commentInfo: commentInfo,
        });
      } else {
        alert("비밀번호가 틀렸습니다.");
      }
    },

    /* 댓글 삭제하기 */
    deleteComment(commentInfo) {
      // console.log("delete comment");
      var input = prompt("비밀번호를 입력하세요");

      if (input == commentInfo.password) {
        this.ACT_DELETE_COMMENT({
          commentId: commentInfo.commentId,
          password: commentInfo.password,
        });
        this.$router.go();
      } else {
        alert("비밀번호가 틀렸습니다.");
      }
    },
  },
};
</script>

<style lang="scss" scoped>
// v-html없이 줄바꿈
.content {
  white-space: pre-line
}
</style>