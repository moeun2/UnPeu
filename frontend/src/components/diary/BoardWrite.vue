<template>
  <v-container fluid>
    <v-card>
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
        category: [(val) => (val || "").length > 0 || "This field is required"],
        title: [(val) => (val || "").length > 0 || "This field is required"],
        content: [(val) => (val || "").length > 0 || "This field is required"],
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
      console.log("edit created");
      console.log(this.value);
      this.form = this.value;
    }
  },

  computed: {
    ...mapGetters(diaryStore, {
      categories: "GET_CATEGORY_LIST",
    }),

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

    save() {
      console.log("save");
      this.AC_REGISTER_BOARD(this.form);
      this.$router.push({ name: "BoardList" });
    },

    edit() {
      console.log("edit");
      this.AC_EDIT_BOARD({
        boardId: this.$route.params.boardId,
        boardInfo: this.form,
      });
      this.$router.push({ name: "BoardList" });
      // this.$router.push({
      //   name: "BoardDetail",
      //   params: { boardId: this.$route.params.boardId },
      // });
    },

    cancle() {
      console.log("cancle");
      this.$router.push({ name: "BoardList" });
    },
  },
};
</script>

<style lang="scss" scoped>
</style>