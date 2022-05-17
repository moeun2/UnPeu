<template>
  <v-container fluid>
    <v-row class="pb-1">
      <v-col cols="3" md="3" class="pa-0 pr-1">
        <v-text-field
          v-model="form.writer"
          :rules="rules.writer"
          label="nickName"
          type="text"
          ref="writer"
          dense
          required
          outlined
          hide-details="auto"
          :readonly="editFlag"
        ></v-text-field>
      </v-col>

      <v-col cols="3" md="3" class="pa-0 pl-1">
        <v-text-field
          v-model="form.password"
          :rules="rules.password"
          label="password"
          type="password"
          ref="password"
          dense
          required
          outlined
          hide-details="auto"
          :readonly="editFlag"
        ></v-text-field>
      </v-col>

      <v-spacer></v-spacer>

      <v-btn color="var(--close-color)" class="mr-1" @click="cancel" outlined depressed>
        취소
      </v-btn>
      <v-btn
        color="primary"
        class="ml-1"
        :disabled="!formIsValid"
        @click="submit"
        depressed
      >
        등록
      </v-btn>
    </v-row>

    <v-row>
      <v-col class="pa-0">
        <v-textarea
          ref="myTextArea"
          v-model="statusContent"
          :rules="rules.content"
          label="Comment"
          rows="1"
          counter
          auto-grow
          outlined
          required
        ></v-textarea>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
const diaryStore = "diaryStore";

export default {
  name: "CommentWrite",

  data() {
    return {
      form: {
        writer: "",
        password: "",
        content: "",
      },

      rules: {
        writer: [(value) =>  (value && 2 <= value.length && value.length <= 8) || "2자 이상 8자 이하 작성"],
        password: [(value) => (value && 4 <= value.length) || "4자 이상 작성"],
        content: [(value) => (value && value.length <= 500) || "1자 이상 500자 이하 작성"],
      },
    };
  },

  created() {
    this.initEditFlag();

    // 이전에 작성했던 닉네임과 비밀먼호가 존재하는가?
    if (this.nonUser.writer && this.nonUser.password) {
      this.form.writer = this.nonUser.writer;
      this.form.password = this.nonUser.password;
    }
  },

  beforeUpdate() {
    // 수정 요청이 들어왔다면
    if (this.editFlag) {
      this.form.writer = this.nonUser.writer;
      this.form.password = this.nonUser.password;

      this.$refs.myTextArea.focus();
    }
  },

  computed: {
    // 이름 지정해서 getters 가져오기
    ...mapGetters(diaryStore, {
      nonUser: "GET_COMMENT_DETAIL",
      editFlag: "GET_EDIT_FLAG",
    }),

    /* 댓글 작성 폼 유효성 검사 */
    formIsValid() {
      var writerLen = this.form.writer.length;
      var pwLen = this.form.password.length;
      return (
        2 <= writerLen && writerLen <= 8 && 4 <= pwLen && this.form.content
      );
    },

    /* 요청한 댓글 내용에 따라 댓글 내용 변경됨 */
    statusContent: {
      get() {
        if (this.editFlag) {
          return this.nonUser.content;
        }
        return "";
      },
      set(value) {
        this.form.content = value;
      },
    },
  },

  methods: {
    ...mapActions(diaryStore, [
      "ACT_REGISTER_COMMENT",
      "ACT_EDIT_COMMENT",
      "ACT_SETTING_COMMENT",
    ]),

    /* 수정체크 플래그 초기화 */
    initEditFlag() {
      this.ACT_SETTING_COMMENT({
        editFlag: false,
      });
    },

    /* 댓글 수정 or 등록 */
    submit() {
      if (this.editFlag) {
        this.edit();
      } else {
        this.save();
      }
    },

    /* 댓글 등록하기 */
    save() {
      // console.log("save comment");
      this.ACT_REGISTER_COMMENT({
        boardId: this.$route.params.boardId,
        commentInfo: this.form,
      });
      this.$router.go(); // 새로고침
    },

    /* 댓글 수정하기 */
    edit() {
      // console.log("edit comment");
      this.ACT_EDIT_COMMENT({
        commentId: this.nonUser.commentId,
        commentInfo: this.form,
      });
      this.$router.go();
    },

    cancel() {
      if (this.editFlag) {
        this.initEditFlag();
      }
      this.$refs.writer.reset();
      this.$refs.password.reset();
      this.$refs.myTextArea.reset();

      // 단순히 reset만 하면 null값이 들어가므로 ""값 입력
      this.form.writer = "";
      this.form.password = "";
      this.form.content = "";
    },
  },
};
</script>

<style lang="scss" scoped>
</style>