<template>
  <v-container fluid>
    <v-row>
      <!-- 비회원 아이디 : 크기 작게 수정-->
      <!-- 아이디 6글자 이상 작성 필요 -->
      <v-col cols="12" md="5">
        <v-text-field
          v-model="tmpAccount.writer"
          :rules="[rules.writer, rules.length(6)]"
          label="nickName"
          counter="6"
          solo
          dense
          filled
          required
        ></v-text-field>
      </v-col>

      <!-- 비회원 비밀번호 : 크기 작게 수정-->
      <!-- 비밀번호 6글자 이상 작성 필요 -->
      <v-col cols="12" md="5">
        <v-text-field
          v-model="tmpAccount.password"
          :rules="[rules.password, rules.length(6)]"
          label="passWord"
          type="password"
          counter="6"
          solo
          dense
          filled
          required
        ></v-text-field>
      </v-col>

      <v-col cols="12" md="10">
        <v-textarea
          v-model="tmpAccount.contents"
          :rules="rules.contents"
          label="Comment"
          rows="1"
          solo
          auto-grow
          outlined
          required
        ></v-textarea>
      </v-col>

      <v-col cols="12" md="2">
        <v-btn @click="save">save</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "CommentWrite",

  data() {
    return {
      tmpAccount: {
        writer: "",
        password: "",
        contents: "",
      },
      rules: {
        writer: [(val) => (val || "").length > 0 || "This field is required"],
        length: (len) => (v) => (v || "").length >= len || `required ${len}`,
        password: [(val) => (val || "").length > 0 || "This field is required"],
        contents: [(val) => (val || "").length > 0 || "This field is required"],
      },
    };
  },

  methods: {
    save() {
      // 저장되면서 작성된 내용 reset 필요
      console.log("save comment");
    },
  },
};
</script>

<style lang="scss" scoped>
</style>