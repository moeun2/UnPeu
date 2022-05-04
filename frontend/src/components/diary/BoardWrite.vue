<template>
  <v-container fluid>
    <v-card outlined>
      <v-card-title>
        <span v-if="this.type === 'write'">Write</span>
        <span v-else>Edit</span>
      </v-card-title>

      <v-card-text>
        <v-form @submit.prevent="submit" data-app="true">
          <v-select
            v-model="form.category"
            :rules="rules.category"
            :items="categories"
            label="Category"
            required
          ></v-select>
          <v-text-field
            v-model="form.title"
            :rules="rules.title"
            label="Title"
            required
          ></v-text-field>
          <!-- CKEditor로 수정 예정 -->
          <v-textarea
            v-model="form.content"
            :rules="rules.content"
            label="Content"
            rows="6"
            auto-grow
            required
          >
          </v-textarea>
        </v-form>
      </v-card-text>

      <v-card-actions>
        <v-btn
          v-if="this.type === 'write'"
          :disabled="!formIsValid"
          @click="save"
          text
        >
          save
        </v-btn>
        <v-btn v-else :disabled="!formIsValid" @click="edit" text> edit </v-btn>
        <v-btn text @click="cancle"> cancle </v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
const diaryStore = "diaryStore";

export default {
  name: "BoardWrite",

  data() {
    return {
      form: {
        userId: this.$route.params.userid,
        category: "",
        title: "",
        content: "",
      },
      rules: {
        category: [(value) => (value && value.length <= 40) || "반드시 1자 이상, 40자 미만 작성해야 합니다."],
        title: [(value) => (value && value.length <= 30)  || "반드시 1자 이상, 30자 미만 작성해야 합니다."],
        content: [(value) => (value && value.length <= 10000) || "반드시 1자 이상, 10000자 미만 작성해야 합니다."],
      },
    };
  },

  props: {
    type: {
      type: String,
      default: "write",
    },

    value: {
      type: Object,
    },
  },

  created() {
    this.AC_CATEGORY_LIST(this.form.userId);

    if (this.type === "edit") {
      // console.log("edit created");
      // console.log(this.value);
      this.form = this.value;
    }
  },

  computed: {
    ...mapGetters(diaryStore, {
      categories: "GET_CATEGORY_LIST",
    }),

    /* 작성 폼 유효성 검사 */
    formIsValid() {
      return this.form.title && this.form.content && this.form.category;
    },
  },

  methods: {
    ...mapActions(diaryStore, [
      "AC_CATEGORY_LIST",
      "AC_REGISTER_BOARD",
      "AC_EDIT_BOARD",
    ]),

    /* 저장 후 상세 페이지 이동 */
    save() {
      // console.log("save");
      this.AC_REGISTER_BOARD(this.form);
    },

    /* 편집 후 상세 페이지 이동 */
    edit() {
      // console.log("edit");
      this.AC_EDIT_BOARD({
        boardId: this.$route.params.boardId,
        boardInfo: this.form,
      });
    },

    /* 뒤로가기 */
    cancle() {
      // console.log("cancle");
      this.$router.go(-1); // 한 단계 뒤로
    },
  },
};
</script>

<style lang="scss" scoped>
</style>