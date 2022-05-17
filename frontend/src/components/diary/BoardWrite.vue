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

          <vue-editor
            id="editor"
            useCustomImageHandler
            :editorOptions="editorSettings"
            @image-added="handleImageAdded"
            v-model="form.content"
            class="myeditor_css text--primary"
            ref="myQuillEditor"
          >
          </vue-editor>
        </v-form>
      </v-card-text>
      
      <!-- <v-card-text></v-card-text>
      <v-card-text></v-card-text> -->
      
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          v-if="this.type === 'write'"
          :disabled="!formIsValid"
          @click="save"
          color="var(--confirm-color)"
          class="white--text"
        >
          save
        </v-btn>
        <v-btn
          v-else
          :disabled="!formIsValid"
          @click="edit"
          color="var(--confirm-color)"
          class="white--text"
        >
          edit
        </v-btn>
        <v-btn
          @click="cancel"
          color="var(--cancel-color)"
          class="ma-2 white--text"
        >
          cancel
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { addBoardFileImage } from "@/api/diary.js";
import { API_BASE_URL } from "@/config/index";
import { VueEditor, Quill } from "vue2-editor";

window.Quill = Quill;
const ImageResize = require("quill-image-resize-module").default;
Quill.register("modules/imageResize", ImageResize);

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
        category: [ (value) => (value && value.length <= 40) || "반드시 1자 이상, 40자 미만 작성해야 합니다."],
        title: [ (value) => (value && value.length <= 30) || "반드시 1자 이상, 30자 미만 작성해야 합니다."],
      },

      editorSettings: {
        modules: {
          imageResize: {},
          toolbar: [
            [{ header: [1, 2, 3, 4, 5, 6, false] }],
            ["italic", "underline", "strike"],
            ["blockquote", "code-block"],
            [{ color: [] }, { background: [] }],
            [
              { align: "" },
              { align: "center" },
              { align: "right" },
              { align: "justify" },
            ],
            [
              { list: "ordered" },
              { list: "bullet" },
              { indent: "-1" },
              { indent: "+1" },
            ],

            ["link", "image", "video"],
          ],
        },
      },
    };
  },

  components: {
    VueEditor,
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
      categoryList: "GET_CATEGORY_LIST",
    }),

    categories: function () {
      var merged = ["Default"].concat(this.categoryList);
      var unique = merged.filter((item, pos) => merged.indexOf(item) === pos); // 중복 제거
      // console.log(unique);
      return unique;
    },

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

    /* 이미지 등록 */
    handleImageAdded(file, Editor, cursorLocation) {
      var formData = new FormData();
      formData.append("file", file);

      addBoardFileImage(
        formData,
        (res) => {
          const url = API_BASE_URL + res.data.url;
          Editor.insertEmbed(cursorLocation, "image", url);
          console.log("이미지 등록 성공");
        },
        (error) => {
          console.log("등록 처리 시 문제가 발생했습니다");
          console.log(error);
        }
      );
    },

    /* 이미지 삭제 */
    // handleImageRemoved(url) {
    //   const savePath = url.replace(API_BASE_URL, "");

    //   deleteBoardFileImage(
    //     savePath,
    //     (res) => {
    //       console.log(res);
    //       console.log("이미지 삭제 성공")
    //     },
    //     (error) => {
    //       console.log("삭제 처리 시 문제가 발생했습니다");
    //       console.log(error);
    //     }
    //   );
    // },

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
    cancel() {
      // console.log("cancel");
      this.$router.go(-1); // 한 단계 뒤로
    },
  },
};
</script>

<style scoped>
@import url("@/assets/css/editor.css");
</style>