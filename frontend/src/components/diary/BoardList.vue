<template>
  <v-container fluid>
    <v-card>
      <v-card-title>
        <span>Diary</span>
      </v-card-title>

      <v-card-subtitle>
        <v-row>
          <v-col>
            <span>{{ today }}</span>
            <v-btn class="float-right" @click="writeDiary"> 글 작성 </v-btn>
          </v-col>
        </v-row>
      </v-card-subtitle>

      <v-card-text>
        <v-row>
          <v-col>
            <v-select
              v-model="category"
              :items="categories"
              label="Category"
              clearable
              dense
            ></v-select>

            <v-data-table
              class="elevation-1"
              :headers="headers"
              :items="boardList"
              :items-per-page="10"
              item-key="title"
              @click:row="moveDetailPage"
            >
            </v-data-table>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import dayjs from "dayjs";
import { mapGetters, mapActions } from "vuex";
const diaryStore = "diaryStore";

export default {
  name: "BoardList",

  data() {
    return {
      today: dayjs().format("YYYY-MM-DD"),
      headers: [
        { text: "Title", align: "start", value: "title" },
        { text: "Date", align: "center", value: "createdAt" },
      ],
      userId: this.$route.params.userid,
      category: "Default",
    };
  },

  created() {
    this.AC_CATEGORY_LIST(this.userId);
    this.AC_BOARD_LIST({ userId: this.userId, category: this.category });
  },

  computed: {
    // 이름 지정해서 getters 가져오기
    ...mapGetters(diaryStore, {
      boardList: "GET_BOARD_LIST",
      categories: "GET_CATEGORY_LIST",
    }),
  },

  watch: {
    //category값이 변할 때마다 함수실행
    category: function () {
      this.AC_BOARD_LIST({ userId: this.userId, category: this.category });
    },
  },

  methods: {
    ...mapActions(diaryStore, ["AC_BOARD_LIST", "AC_CATEGORY_LIST"]),

    writeDiary() {
      this.$router.push({ name: "BoardWrite" });
    },

    moveDetailPage(data) {
      console.log(data.boardId);
      this.$router.push({
        name: "BoardDetail",
        params: { boardId: data.boardId },
      });
    },
  },
};
</script>

<style lang="scss" scoped></style>
