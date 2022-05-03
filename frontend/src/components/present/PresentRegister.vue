<template>
<div>
<v-form @submit.prevent="regist">
<v-container fluid>
  <v-row>
    <v-col cols="3">
      <v-file-input
        :rules="rules"
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
        ></v-text-field>
      </v-col>
    <v-col cols="3">
      <v-text-field
          v-model="presentPrice"
          label="Amount"
          value="10.00"
          prefix="₩"
        ></v-text-field>
      </v-col>
      <v-col cols="2">
      <v-btn
      rounded
      color="primary"
      dark
      type="submit"
    >
      등록하기
    </v-btn>
      </v-col>

        </v-row>
      </v-container>
    </v-form>
</div>
</template>

<script>
import {mapActions} from "vuex";
const presentStore="presentStore";
  export default {
    data: () => ({
      presentName: null,
      presentPrice: null,
      rules: [
        value => !value || value.size < 2000000 || 'Avatar size should be less than 2 MB!',
      ],
      files:[],
      
    }),
    computed:{
    },
    methods:{
      ...mapActions(presentStore,["registerPresent"]),
       regist(){
         this.$swal.fire({
        title: '등록하시겠어요?',
        text: "작성한 선물을 등록합니다!",
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: '네, 등록합니다!'
      }).then((result) => {
        if (result.isConfirmed) {
          console.log(this.present);
        
        let fd=new FormData();
        fd.append('presentImg',this.files);
        fd.append('presentPrice',this.presentPrice);
        fd.append('userId', this.$store.state.userStore.user.id);
        fd.append('presentName',this.presentName);

        let rgst=this.registerPresent(fd);
        if(!rgst){
          this.$swal.fire(
            '등록 실패!',
            '파일이 정상적으로 등록되지 않았습니다.',
            'error'
          )
          
        }else{
          this.$swal.fire(
            '등록 성공!',
            '파일이 등록되었습니다.',
            'success'
          )
          this.presentName= null;
          this.presentPrice= null;
          this.files=[];
        }
        }
      })
        
      }
    }
  }
</script>

<style>

</style>