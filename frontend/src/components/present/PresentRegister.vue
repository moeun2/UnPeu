<template>
<div>
<v-form
ref="form"
>
<v-container fluid>
  <v-row>
    <v-col cols="3">
      <v-file-input
        :rules="rules.files"
        v-model="files"
        accept="image/*"
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
        :rules="rules.amount"
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
      @click="regist()"
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
      rules: {
        files:[value => {!value || value.size < 2000000 || '파일 크기가 2MB 이하여야합니다!'}],
         amount:[v  => {
           console.log(!v);
          if (!isNaN(parseFloat(v)) && v >= 0) return true;
          return '정확한 금액을 입력해주세요';
        }]
      },
      files:[],
      
    }),
    computed:{
    },
    methods:{
      ...mapActions(presentStore,["registerPresent"]),
      // inputChanged() { 
      //   if(this.files === null){
      //     this.show=false;
      //     return;
      //   }
      //   console.log('file type=',this.files.type)
      //   if (this.files.type.match('image/*'))
      //     {  console.log('image matched');
      //         this.show=false;
      //     }
      //   else{ console.log('not matched')
      //         this.show=true;
      //         this.message="이미지가 아닙니다" ;
      //       }
      //   },
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