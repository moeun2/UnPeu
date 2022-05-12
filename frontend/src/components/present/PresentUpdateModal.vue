<template>
  <v-dialog v-model="dialog" max-width="600px" @click:outside="$emit('close')">
    <template>
      <v-card>
        <v-card-title>
          <v-row justify="center">
          <span>선물 수정하기</span>
          </v-row>
        </v-card-title>
        <v-divider></v-divider>
        <v-tabs
          v-model="tab"
          background-color="yellow lighten-4"
          color="blue accent-2"
          grow
        >
          <v-tab
            v-for="item in items"
            :key="item"
            style="font-size: 20px;"
          >
            {{ item }}
          </v-tab>
        <v-tab-item>
          <v-card flat>
            <v-card-text>
              <v-form ref="form1">
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="6">
                    <v-text-field
                      :rules="rules.name"
                      v-model="updatedPresentExceptFile.presentName"
                      label="선물 이름"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="6">
                    <v-text-field
                      :rules="rules.price"
                      v-model="updatedPresentExceptFile.presentPrice"
                      required
                      label="가격 설정"
                      value="0"
                      prefix="₩"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
              depressed
              color="primary"
              style="margin-right: 5px" 
              @click="update()"> 보내기 </v-btn>
              <v-btn depressed color="warning" @click="$emit('close')">
                닫기
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-tab-item>
        <v-tab-item>
          <v-card flat>
            <v-card-text>
              <v-form ref="form2">
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="6">
                    <v-text-field
                      :rules="rules.name"
                      v-model="updatedPresent.presentName"
                      label="선물 이름"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="6">
                    <v-text-field
                      :rules="rules.price"
                      v-model="updatedPresent.presentPrice"
                      required
                      label="가격 설정"
                      value="0"
                      prefix="₩"
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-row justify="center">
                  <span style="font-size:25px;">현재 선물</span>
                </v-row>
                <v-row justify="center">
                  <v-col col="12" sm="6" align-self="center">
                    <v-img class="mx-auto" :src="imgUrl" aspect-ratio="0.8">
                    </v-img>
                  </v-col>
                </v-row>
                <v-row justify="center">
                  <v-col col="12" sm="6">
                    <v-layout>
                      <v-file-input
                        :rules="rules.file"
                        v-model="files"
                        required
                        accept="image/png, image/jpeg, image/bmp"
                        placeholder="변경하고 싶은 선물 선택!"
                        prepend-icon="mdi-camera"
                      ></v-file-input>
                    </v-layout>
                  </v-col>
                </v-row>
              </v-container>
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
              depressed
              color="primary"
              style="margin-right: 5px" 
              @click="updateWithFile()"> 보내기 </v-btn>
              <v-btn depressed color="warning" @click="$emit('close')">
                닫기
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-tab-item>
      </v-tabs>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import { mapActions } from "vuex";
import { API_BASE_URL } from "../../config/index.js";

const presentStore = "presentStore";

export default {
  props: ["openDialog", "present"],

  data() {
    return {
      items: [
        '이름과 가격만 수정','전체 수정'
      ],
      files: [],
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
      ],},
      presentId: null,
      newPresentImg: null,
      updatedPresentExceptFile:{
        presentName: null,
        presentPrice: null,
      },
      updatedPresent: {
        presentName: null,
        presentPrice: null,
        presentImg: null,
        userId: this.$store.state.userStore.user.id,
      },
      API_BASE_URL: API_BASE_URL,
      imgUrl: null,
    };
  },
  
  computed: {
    dialog() {
      // console.log(this.openDialog);
      return this.openDialog;
    },
  },

  mounted() {
    if (this.present === null) this.updatedPresent.presentName = null;
    else this.updatedPresent.presentName = this.present.presentName;
    if (this.present === null) this.updatedPresent.presentPrice = null;
    else this.updatedPresent.presentPrice = this.present.presentPrice;
    if (this.present === null) this.imgUrl = null;
    else this.imgUrl = this.API_BASE_URL + this.present.presentImg;
    if (this.present === null) this.updatedPresentExceptFile.presentName = null;
    else this.updatedPresentExceptFile.presentName = this.present.presentName;
    if (this.present === null) this.updatedPresentExceptFile.presentPrice = null;
    else this.updatedPresentExceptFile.presentPrice = this.present.presentPrice;
  },

  watch: {
    present() {
      this.updatedPresent.presentName = this.present.presentName;
      this.updatedPresent.presentPrice = this.present.presentPrice;
      this.imgUrl = this.API_BASE_URL + this.present.presentImg;
      this.updatedPresentExceptFile.presentName = this.present.presentName;
      this.updatedPresentExceptFile.presentPrice = this.present.presentPrice;
    },
  },

  methods: {
    ...mapActions(presentStore, ["updatePresent"]),
    errorAlert(message) {
      this.$swal.fire("Oops...!", message, "error");
    },

    closeDialog() {
      this.dialog = false;
    },

    update() {
      let valid=this.$refs.form1.validate();
      if(!valid){
        this.errorAlert("모든 항목들을 정확히 등록해주세요!");
      }
      else{
        let fd = new FormData();
        fd.append("presentPrice", this.updatedPresentExceptFile.presentPrice);
        fd.append("userId", this.$store.state.userStore.user.id);
        fd.append("presentName", this.updatedPresentExceptFile.presentName);

        let presentData = {
          fd: fd,
          presentId: this.present.presentId,
        };
        this.updatePresent(presentData);
        this.$emit("close");
      }
    },

    updateWithFile() {
      console.log(this.files.length);
      if(this.files==null || this.files.length==0)
        this.errorAlert("파일을 넣어주세요");
      if(this.files.type.indexOf("image/")==-1){
        this.errorAlert("파일 타입을 확인해주세요. 이미지만 가능합니다");
        return;
      }
      let valid=this.$refs.form2.validate();
      if(!valid){
        this.errorAlert("모든 항목들을 정확히 등록해주세요!");
      }
      else{
        let fd = new FormData();
        fd.append("presentImg", this.files);
        fd.append("presentPrice", this.updatedPresent.presentPrice);
        fd.append("userId", this.$store.state.userStore.user.id);
        fd.append("presentName", this.updatedPresent.presentName);

        let presentData = {
          fd: fd,
          presentId: this.present.presentId,
        };
        // console.log(presentData);
        this.updatePresent(presentData);
        this.files=null;
        this.$emit("close");
      }
    },
  },
};
</script>

<style>
</style>