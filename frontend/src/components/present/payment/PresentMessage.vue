<template>
  <div>
    <v-form ref="form" @submit.prevent="sendMessage">
      <v-row justify="space-between" style="margin: 5px">
        <v-alert dense type="info">
          주의 : 보내진 메세지는 수정이 불가하며, 받는 사람만 읽을 수 있으니
          보내실 때 내용을 확인해주세요
        </v-alert>
      </v-row>
      <v-text-field
        v-model="nickname"
        solo
        label="닉네임"
        clearable
        :rules="rules.nickname"
      ></v-text-field>
      <v-textarea
        v-model="content"
        solo
        name="input-7-4"
        label="메시지 내용"
        :rules="rules.content"
      ></v-textarea>
      <v-row justify="space-between" style="margin: 5px">
        <v-alert dense type="info">
          선물을 <strong>선택</strong>할 시 결제 후 완료 메시지가 보내집니다<br />
          <strong
            >현재, 선물 결제시 100원만 가결제되며, 이는 24시 이전에
            취소됩니다.</strong
          >
        </v-alert>
        <v-btn tile color="success" type="submit">
          <v-icon left> mdi-email-edit </v-icon>
          보내기
        </v-btn>
      </v-row>
    </v-form>
  </div>
</template>

<script>
import { API_BASE_URL } from "@/config/index.js";

export default {
  data() {
    return {
      nickname: "",
      content: "",
      cardList: [],
      API_BASE_URL: API_BASE_URL,
      rules: {
        nickname: [
          (v) => !!v || "닉네임은 필수 입력사항입니다.",
          (v) =>
            !/[/\s/]/.test(v) ||
            "공백은 들어갈 수 없습니다. 띄어쓰기 사용 시 '-' 로 사용해주세요",
          (v) => !(v && v.length > 10) || this.lengthError(10, 1),
        ],
        content: [
          (v) => !!v || "내용은 필수 입력사항입니다.",
          (v) =>
            !(v && v.length < 5) ||
            "내용은 5자 이상 500자 이하로 입력해주세요.",
          (v) => !(v && v.length > 500) || this.lengthError(500, 2),
        ],
      },
    };
  },

  methods: {
    errorAlert(message) {
      this.$swal.fire("Oops...!", message, "error");
    },
    lengthError(len, idx) {
      let current = "";
      if (idx == 1) {
        console.log("!!!");
        // this.nickname = this.nickname.substring(0,len);
        current = this.nickname.length;
      } else {
        // this.content = this.content.substring(0, len);
        current = this.content.length;
      }
      //this.$swal.fire("Oops...!", len+"자 이하로 적어주세요!", "error");
      return (
        "(" + current + "/" + len + ")" + " 글자수는 1자 이상 10자 이하입니다!"
      );
    },

    /**
     * @Click : 보내기 버튼
     * 메세지 내용 검사
     * 부모 컴포넌트로 Emit
     */
    sendMessage() {
      const validate = this.$refs.form.validate();
      if (!validate) {
        this.errorAlert("모든 항목들을 확인해주세요!");
      } else {
        this.$emit("message", {
          sender: this.nickname,
          content: this.content,
        });
      }
      // if (this.nickname == null) {
      //   this.$swal.fire("Oops...!", "NickName을 적어주세요!", "error");
      // } else if (this.content == null) {
      //   this.$swal.fire("Oops...!", "메세지를 적어주세요!", "error");
      // } else {

      // }
    },
  },
};
</script>

<style></style>
