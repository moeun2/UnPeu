<template>
  <v-container>
    <v-simple-table dense>
          <tbody v-if="commentList.length">
            <tr
              v-for="item in commentList"
              :key="item.commentId"
            >
              <td><v-icon>mdi-account</v-icon>{{ item.writer }}</td>
              <td>{{ item.contents }}</td>
              <td>{{ item.createdAt }}</td>
            </tr>
          </tbody>
        </v-simple-table>
  </v-container>
</template>

<script>
export default {
  name: 'CommentList',

  data() {
    return {
      commentList: [
        {
          commentId: 1,
          writer: "test1",
          contents: "test중입니다",
          createdAt: "2022-04-19 13:07:58",
        },
        {
          commentId: 2,
          writer: "test2",
          contents: "test중입니다",
          createdAt: "2022-04-20 13:07:58",
        },
      ],
    };
  },

  mounted() {
    
  },

  methods: {
    
  },
};
</script>

<style lang="scss" scoped>

</style>