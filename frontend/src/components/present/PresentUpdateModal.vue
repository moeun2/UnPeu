<template>
  <v-dialog
    v-model="dialog"
    max-width="600px"
    @click:outside="$emit('close')"
  >
  <template>
    <v-card>
      <v-card-title>
        <span class="text-h5">선물 수정하기</span>
      </v-card-title>
      <v-divider></v-divider>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col
              cols="12"
              sm="6"
              md="6"
            >
              <v-text-field
              v-model="updatedPresent.presentName"
                label="선물 이름"
                required
              ></v-text-field>
            </v-col>
            <v-col
              cols="12"
              sm="6"
              md="6"
            >
              <v-text-field
                  v-model="updatedPresent.presentPrice"
                  required
                  label="가격 설정"
                  value="0"
                  prefix="₩"
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row
          justify="center"
          >
          <span class="text-h5">현재 선물</span>
          </v-row>
          <v-row
          justify="center"
          >
            <v-col
            col="12"
            sm="6"
            align-self="center"
            >
              <v-img
                class="mx-auto"
                :src="imgUrl"
                aspect-ratio="0.8"
              >
              </v-img>
            </v-col>
            </v-row>
            <v-row
            justify="center"
            >
            <v-col
            col="12"
            sm="6"
            >  
              <v-layout>
              <v-file-input
                :rules="rules"
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
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          color="blue darken-1"
          text
          @click="update()"
        >
          보내기
        </v-btn>
        <v-btn
          color="blue darken-1"
          text
          @click="$emit('close')"
        >
          닫기
        </v-btn>
      </v-card-actions>
    </v-card>
  </template>
  </v-dialog>
</template>

<script>
import {mapActions} from "vuex";
import {API_BASE_URL} from "../../config/index.js";

const presentStore="presentStore";
  export default {
    props:['openDialog','present'],
    data(){
      return{
        files:[],
        rules: [
        value => !value || value.size < 2000000 || '사진 크기는 2 MB 이하여야 해요!',
        ],
        presentId:null,
        newPresentImg:null,
        updatedPresent: {
          presentName : null,
          presentPrice : null,
          presentImg : null,
          userId : this.$store.state.userStore.user.id,
        },
        API_BASE_URL:API_BASE_URL,
        imgUrl:null,
      }
    },
    computed:{
      dialog(){
        // console.log(this.openDialog);
        return this.openDialog;
      },
    },
    mounted(){
        if(this.present===null) this.updatedPresent.presentName=null;
        else this.updatedPresent.presentName=this.present.presentName;
        if(this.present===null) this.updatedPresent.presentPrice=null;
        else this.updatedPresent.presentPrice=this.present.presentPrice;
        if(this.present===null) this.imgUrl=null;
        else this.imgUrl=this.API_BASE_URL+this.present.presentImg;
    },
    watch:{
      present(){
        this.updatedPresent.presentName=this.present.presentName;
        this.updatedPresent.presentPrice=this.present.presentPrice;
        this.imgUrl=this.API_BASE_URL+this.present.presentImg;
      }
    },
    methods:{
      ...mapActions(presentStore,["updatePresent"]),
      closeDialog(){
        this.dialog=false;
      },
      update(){        
        let fd=new FormData();
        fd.append('presentImg',this.files);
        fd.append('presentPrice',this.updatedPresent.presentPrice);
        fd.append('userId',this.$store.state.userStore.user.id);
        fd.append('presentName',this.updatedPresent.presentName);

        let presentData={
          fd:fd,
          presentId:this.present.presentId,
        }
        // console.log(presentData);
        this.updatePresent(presentData);
        this.$emit('close');
      },
    }
  }
</script>

<style>

</style>