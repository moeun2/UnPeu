<template>
  <v-container fluid>
    <v-card outlined style="height: 515px">
      <v-card-title>
        <span>Diary</span>
      </v-card-title>

      <v-card-subtitle>
        <v-row>
          <v-col>
            <span>{{ today }}</span>
            <v-btn
              v-if="loginUser && userId == loginUser.id"
              color="var(--confirm-color)"
              class="float-right white--text"
              @click="writeDiary"
              ><v-icon left>mdi-pencil</v-icon>글 작성
            </v-btn>
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
              hide-default-footer
              :headers="headers"
              :items="boardList"
              :page.sync="currentPage"
              :items-per-page="5"
              item-key="title"
              @click:row="moveDetailPage"
              @page-count="pageCount = $event"
            >
            </v-data-table>
            <div class="text-center pt-2">
              <v-pagination v-model="currentPage" :length="pageCount"></v-pagination>
            </div>
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
      loginUser: this.$store.state.userStore.user,
      userId: this.$route.params.userid,
      category: "Default",
      currentPage: 1, // 현재 페이지
      pageCount: 0,
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
      categoryList: "GET_CATEGORY_LIST",
    }),

    categories: function () {
      var merged = ["Default"].concat(this.categoryList);
      var unique = merged.filter((item, pos) => merged.indexOf(item) === pos); // 중복 제거
      // console.log(unique);
      return unique;
    },
  },

  watch: {
    //category값이 변할 때마다 함수실행
    category: function () {
      this.AC_BOARD_LIST({ userId: this.userId, category: this.category });
    },
  },

  methods: {
    ...mapActions(diaryStore, ["AC_BOARD_LIST", "AC_CATEGORY_LIST"]),

    /* 작성하기 */
    writeDiary() {
      this.$router.push({ name: "BoardWrite" });
    },

    /* 상세 페이지 이동*/
    moveDetailPage(data) {
      // // // console.log(data.boardId);
      this.$router.push({
        name: "BoardDetail",
        params: { boardId: data.boardId },
      });
    },
  },
};
</script>

<style lang="scss" scoped></style>
