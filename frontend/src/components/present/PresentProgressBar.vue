<template>
  <v-progress-linear
    :value="computedPrice"
    color="amber"
    height="15"
    >
    <template v-slot:default="{ value }">
      <strong>{{ Math.ceil(value) }}%</strong>
    </template>
  </v-progress-linear>
</template>

<script>
export default {
  props: ["presentPrice", "receivedPrice"],
  data(){
    return{
      computedPrice: this.receivedPrice/this.presentPrice*100,
    }
  },
  watch:{
    presentPrice(){
      this.computedPrice=this.receivedPrice/this.presentPrice*100
    },
    receivedPrice(){
      this.computedPrice=this.receivedPrice/this.presentPrice*100
    }
  }
}
</script>

<style>

</style>