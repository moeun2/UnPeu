<template>
<v-form @submit.prevent="registPresent">
<v-container fluid>
  <v-row>
    <v-col cols="5">
        <v-file-input
      :rules="rules"
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
</template>

<script>
  export default {
    data: () => ({
      present: null,
      rules: [
        value => !value || value.size < 2000000 || 'Avatar size should be less than 2 MB!',
      ],
    }),
    methods:{
      registPresent(){
        console.log(this.present);
      }
    }
  }
</script>

<style>

</style>