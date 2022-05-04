<template>
  <div>
    <v-form ref="form" @submit.prevent="regist">
      <v-container fluid>
        <v-row>
          <v-col cols="3">
            <v-file-input
              :rules="rules.file"
              v-model="files"
              accept="image/png, image/jpeg, image/bmp"
              placeholder="이미지 등록하기"
              prepend-icon="mdi-camera"
            ></v-file-input>
          </v-col>
          <v-col cols="3">
            <v-text-field
              v-model="presentName"
              label="선물 이름 등록"
              value="10.00"
              :rules="rules.name"
            ></v-text-field>
          </v-col>
          <v-col cols="3">
            <v-text-field
              v-model="presentPrice"
              label="Amount"
              value="10.00"
              prefix="₩"
              :rules="rules.price"
            ></v-text-field>
          </v-col>
          <v-col cols="2">
            <v-btn rounded color="primary" dark type="submit"> 등록하기 </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
  </div>
</template>

<script>
import { mapActions } from "vuex";
const presentStore = "presentStore";
export default {
  data: () => ({
    presentName: null,
    presentPrice: null,
    rules: {
      file: [
        (value) =>
          !value ||
          value.size < 2000000 ||
          "Avatar size should be less than 2 MB!",
      ],
      name: [
        (v) => !!v || "선물이름은 필수 입력사항입니다.",
        (v) =>
          !/[~!@#$%^&*()_+|<>?:{}]/.test(v) ||
          "선물이름에는 특수문자를 사용할 수 없습니다.",
        (v) =>
          !/[/\s/g]/.test(v) ||
          "공백은 들어갈 수 없습니다. 띄어쓰기 사용 시 '-' 로 사용해주세요",
        (v) =>
          !(v && v.length >= 10) ||
          "선물이름은 1자 이상 10자 이하로 입력해주세요.",
      ],
      price: [
        (v) => !!v || "가격은 필수 입력사항입니다.",
        (v) => !/[^0-9]/.test(v) || "가격은 숫자만 입력가능합니다.",
        (v) => !/[/\s/g]/.test(v) || "공백은 들어갈 수 없습니다",
        (v) => !(v && v < 0) || "가격은 음수가 들어갈 수 없습니다",
        (v) =>
          !(v && v.length >= 10) || "가격은 3자 이상 10자 이하로 입력해주세요.",
        (v) =>
          !(v && v.length < 3) || "가격은 3자 이상 10자 이하로 입력해주세요.",
      ],
    },
    files: [],
  }),
  computed: {},
  methods: {
    errorAlert(message) {
      this.$swal.fire("Oops...!", message, "error");
    },
    ...mapActions(presentStore, ["registerPresent"]),
    regist() {
      const validate = this.$refs.form.validate();
      if (!validate) {
        this.errorAlert("모든 항목들을 등록해주세요!");
      } else {
        this.$swal
          .fire({
            title: "등록하시겠어요?",
            text: "작성한 선물을 등록합니다!",
            icon: "question",
            showCancelButton: true,
            confirmButtonText: "네, 등록합니다!",
          })
          .then((result) => {
            if (result.isConfirmed) {
              console.log(this.present);

              let fd = new FormData();
              fd.append("presentImg", this.files);
              fd.append("presentPrice", this.presentPrice);
              fd.append("userId", this.$store.state.userStore.user.id);
              fd.append("presentName", this.presentName);

              let rgst = this.registerPresent(fd);

              if (!rgst) {
                this.$swal.fire(
                  "등록 실패!",
                  "파일이 정상적으로 등록되지 않았습니다.",
                  "error"
                );
              } else {
                this.$swal.fire(
                  "등록 성공!",
                  "파일이 등록되었습니다.",
                  "success"
                );
                this.presentName = null;
                this.presentPrice = null;
                this.files = [];
              }
            }
          });
      }
    },
  },
};
</script>

<style></style>
