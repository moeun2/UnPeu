<template>
  <div>
    <v-row justify="space-between" style="margin:5px;">
      <v-alert dense type="info">
        주의 : 보내진 메세지는 수정이 불가하며, 받는 사람만 읽을 수 있으니 보내실 때 내용을 확인해주세요
      </v-alert>
    </v-row>
    <v-text-field
      v-model="nickname"
      solo
      label="닉네임"
      clearable
    ></v-text-field>
    <v-textarea
      v-model="content"
      solo
      name="input-7-4"
      label="메시지 내용"
      :rules="rules.content"
    ></v-textarea>
    <v-row justify="space-between" style="margin:5px;">
      <v-alert dense type="info">
        선물을 <strong>선택</strong>할 시 결제 후 완료 메시지가 보내집니다<br />
        <strong
          >현재, 선물 결제시 100원만 가결제되며, 이는 24시 이전에
          취소됩니다.</strong
        >
      </v-alert>
      <v-btn tile color="success" type="submit" @click="sendMessage">
        <v-icon left> mdi-email-edit </v-icon>
        보내기
      </v-btn>
    </v-row>
  </div>
</template>

<script>
import { API_BASE_URL } from "../../config/index.js";
export default {
  data() {
    return {
      nickname: null,
    content: null,
    cardList: [],
    API_BASE_URL: API_BASE_URL,
      rules: {
        content: [
          (val) => val.length<500 || "500자 이하로 적어주세요"
        ],
      },
    };
  },
  methods: {
    /**
     * @Click : 보내기 버튼
     * 메세지 내용 검사
     * 부모 컴포넌트로 Emit
     */
    sendMessage() {
      if (this.nickname == null) {
        this.$swal.fire("Oops...!", "NickName을 적어주세요!", "error");
      } else if (this.content == null) {
        this.$swal.fire("Oops...!", "메세지를 적어주세요!", "error");
      } else {
        this.$emit("message", {
          sender: this.nickname,
          content: this.content,
        });
      }
    },
  },
};
</script>

<style></style>
