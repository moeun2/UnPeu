<template>
  <div>
    <v-text-field
            v-model="message2"
            solo
            label="닉네임"
            clearable
          ></v-text-field>
    <v-textarea
          solo
          name="input-7-4"
          label="메시지 내용"
        ></v-textarea>
    <v-row
    justify="space-around"
  >
    <v-alert
      dense
      type="info"
    >
      선물을 <strong>선택</strong>할 시 결제 후 메시지가 보내집니다
    </v-alert>
    <v-btn
      tile
      color="success"
    >
      <v-icon left>
        mdi-email-edit
      </v-icon>
      보내기
    </v-btn>
    
  </v-row>
  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>