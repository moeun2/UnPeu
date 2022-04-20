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
            v-model="form.contents"
            :rules="rules.contents"
            label="Contents"
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
export default {
  name: "BoardWrite",

  data() {
    return {
      form: {
        category: "",
        title: "",
        contents: "",
      },
      rules: {
        category: [(val) => (val || "").length > 0 || "This field is required"],
        title: [(val) => (val || "").length > 0 || "This field is required"],
        contents: [(val) => (val || "").length > 0 || "This field is required"],
      },
      // 모든 data는 restApi를 통해 값을 가져온다. (List 형태)
      categories: ["List", "2022_어른이날"],
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

  mounted() {
    if (this.type === "edit") {
      this.form = this.value;
    }
  },

  computed: {
    formIsValid() {
      return this.form.title && this.form.contents && this.form.category;
    },
  },

  methods: {
    save() {
      console.log("save");
    },
    edit() {
      console.log("edit");
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