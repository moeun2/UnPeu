<template>
<div>
<v-form @submit="regist">
<v-container fluid>
  <v-row>
    <v-col cols="5">
      <v-file-input
        :rules="rules"
        v-model="files"
        accept="image/png, image/jpeg, image/bmp"
        placeholder="이미지 등록하기"
        prepend-icon="mdi-camera"
      ></v-file-input>
    </v-col>
    <v-col cols="5">
      <v-text-field
          v-model="present"
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
      present: null,
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
        console.log(this.present);
        
        let fd=new FormData();
        fd.append('presentImg',this.files);
        fd.append('presentPrice',this.present);
        fd.append('userId',0);
        fd.append('presentName',this.files.name);

        for (var key of fd.keys()) {

        console.log(key);

      }

        for (var value of fd.values()) {

          console.log(value);

        }

         this.registerPresent(fd);
      }
    }
  }
</script>

<style>

</style>