<template>
  <v-container fluid>
    <v-card height="400">
      <v-card-title>
        <span>Diary</span>
      </v-card-title>

      <v-card-subtitle>
        <span>{{ today }}</span>
        <v-btn @click="writeDiary"> 글 작성 </v-btn>
      </v-card-subtitle>

      <v-card-text data-app="true">
        <v-select
          v-model="category"
          :items="categories"
          label="Category"
          return-object
          single-line
        ></v-select>

        <v-simple-table>
          <thead>
            <tr>
              <th>title</th>
              <th>Date</th>
            </tr>
          </thead>
          <tbody v-if="boardList.length">
            <tr
              v-for="item in boardList"
              :key="item.diaryId"
              @click="moveDetailPage(item.diaryId)"
            >
              <td>{{ item.title }}</td>
              <td>{{ item.createdAt }}</td>
            </tr>
          </tbody>
        </v-simple-table>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import dayjs from "dayjs";

export default {
  name: "BoardList",

  data() {
    return {
      today: dayjs().format("YYYY-MM-DD"),
      category: "",
      // 모든 data는 restApi를 통해 값을 가져온다. (List 형태)
      categories: ["List", "2022_어른이날"],
      boardList: [
        // select값에 따라 값이 변한다.
        {
          diaryId: 1,
          title: "0412 일기",
          createdAt: "2022-04-12",
        },
        {
          diaryId: 2,
          title: "0413 일기",
          createdAt: "2022-04-13",
        },
      ],
    };
  },

  methods: {
    writeDiary() {
      this.$router.push({ name: "BoardWrite" });
    },
    moveDetailPage(id) {
      // 행의 id값을 이용하여 세부사항 조회
      console.log(id);
      this.$router.push({ name: "BoardDetail", params: { diaryId: id } });
    },
  },
};
</script>

<style lang="scss" scoped></style>
