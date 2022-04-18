<template>
  <div class="view">
    <HelloWorld/>
  </div>
</template>

<script>
import HelloWorld from './components/HelloWorld';

export default {
  name: 'App',

  components: {
    HelloWorld,
  },

  data: () => ({
    //
  }),
};
</script>

<style lang="css">
.view {
  margin-left: 120px;
}
body {
  background-image: url("https://i.imgur.com/EI9xcZH.png");
  background-size: cover;
}
</style>
