<template>
  <div class="present-carousel">
    <!-- <v-button >선물등록이 필요합니다</v-button> -->
    <template v-if="cardList.length === 0">
      <div v-if="this.curUser.permission == 0">
        <v-btn tile color="info" @click="goToPresentManage">
          <v-icon left> mdi-exclamation </v-icon>
          선물이 등록되지 않았어요!<br />
          선물 등록하러 가시겠어요? :)
        </v-btn>
        <br />
        <br />
      </div>
      <!-- <div v-else>
        <v-img src="@/assets/img/none_present.png"> </v-img>
      </div> -->
    </template>

    <template v-else>
      <v-alert dense type="info">
        선물은 한 가지만 선택할 수 있어요! 현재 선택한 선물의 가격은
        <strong>{{ selectedPresentPrice }} 원</strong> 입니다!
      </v-alert>
      <br />
      <v-carousel hide-delimiters style="height: 400px">
        <template v-for="(card, index) in cardList">
          <v-carousel-item
            v-if="(index + 1) % columns === 1 || columns === 1"
            :key="index"
          >
            <v-row class="flex-nowrap" id="rootCards">
              <template v-for="(n, i) in columns">
                <template v-if="+index + i - 1 <= cardList.length">
                  <v-col :key="i">
                    <v-card
                      v-if="+index + i < cardList.length"
                      class="card"
                      @click="openPayModal(cardList[+index + i], +index + i)"
                    >
                      <v-img
                        :src="API_BASE_URL + cardList[+index + i].presentImg"
                        aspect-ratio="0.8"
                      ></v-img>
                      <v-card-title primary-title>
                        {{ cardList[+index + i].presentName }}
                      </v-card-title>
                      <v-card-text>
                        <p>가격 : {{ cardList[+index + i].presentPrice }}</p>
                        <present-progress-bar 
                          :presentPrice="cardList[+index + i].presentPrice"
                          :receivedPrice="cardList[+index + i].receivedPrice"
                          ></present-progress-bar>
                      </v-card-text>
                    </v-card>
                  </v-col>
                </template>
              </template>
            </v-row>
          </v-carousel-item>
        </template>
      </v-carousel>
    </template>
    <PresentPayModal
      v-if="showModal"
      @close="closePayModal"
      @selectedPrice="saveSelectedPresent"
    >
      <img
        :src="this.selectedPresentImg"
        :alt="this.selectedPresentName"
        class="modal-img"
        slot="body"
      />
    </PresentPayModal>
  </div>
</template>

<script>
import { API_BASE_URL } from "@/config/index.js";
import { mapGetters, mapActions, mapState } from "vuex";
import PresentPayModal from "@/components/present/payment/PresentPayModal";
import PresentProgressBar from "@/components/present/PresentProgressBar.vue";

const presentStore = "presentStore";
const userStore = "userStore";
export default {
  components: {
    PresentPayModal,
    PresentProgressBar,
  },
  data() {
    return {
      cardList: [],
      API_BASE_URL: API_BASE_URL,
      showModal: false,
      selectedPresentId: "",
      selectedPresentPrice: 0,
      selectedPresentImg: "",
      selectedPresentName: "",
      currentIdx: "",
      showCarousel: true,
    };
  },
  mounted() {
    this.search();
  },
  watch: {
    ...mapState(presentStore, ["presentList"]),
    presentList: {
      deep: true,
      handler(newVal) {
        if (newVal == null) return;
        this.cardList = newVal.Present;
      },
    },
  },
  computed: {
    ...mapGetters(userStore, {
      curUser: "getCurUser",
    }),
    ...mapState(presentStore, ["presentList"]),
    columns() {
      return 3; // 카드 개수 3개로 고정
    },
  },
  methods: {
    ...mapActions(presentStore, ["searchList"]),
    goToPresentManage() {
      this.$router.push({ name: "PresentManage" });
    },
    /**
     * BackEnd에서 getPresentListByUserId 호출 함수
     */
    search() {
      // To Do: User State에서 가져오기
      //console.log("PresentSelectListSearch_search - 호출");
      //console.log("userId: ", this.curUser.id);
      this.searchList(this.curUser.id);
      this.cardList = this.presentList.Present;
      //console.log(this.cardList.length);
      if (this.cardList.length == 0) {
        this.showCarousel = false;
      } else {
        this.showCarousel = true;
      }
    },
    /**
     * PayModal Open시 실행되는 함수
     */
    openPayModal(card, idx) {
      console.log("openPayModal",idx);
      this.selectedPresentImg = API_BASE_URL + card.presentImg;
      this.selectedPresentName = card.presentName;
      this.selectedPresentId = card.presentId;
      this.currentIdx = idx;
      this.changeCardColor(true);
      this.showModal = true;
    },
    /**
     * PayModal close시 실행되는 함수
     */
    closePayModal() {
      this.selectedPresentPrice = 0;
      this.selectedPresentId=null;
      this.showModal = false;
      this.changeCardColor(false);
    },
    /**
     * PayModal에서 가격선택 시 넘어오는 함수
     * Present component로 선택된 Present 정보 Emit
     */
    saveSelectedPresent(data) {
      //console.log("payModal에서 받아온 selectedPrice : " + data);
      this.showModal = false;
      this.selectedPresentPrice = data;
      this.$emit("present", {
        selectedPresentId: this.selectedPresentId,
        selectedPresentPrice: this.selectedPresentPrice,
        selectedCardIdx: this.currentIdx,
      });
    },

    /**
     * 선택된 카드 색상을 바꿔주는 함수
     * 현재 노란색으로 지정해놓았으며, 나중에 색깔을 통일할 예정(style : .selectedCard 참고)
     */
    changeCardColor(reverse) {
      console.log(this.currentIdx);
      const cardLength = document.getElementById("rootCards").children.length;
      for (let index = 0; index < cardLength; index++) {
          const er =
            document.getElementById("rootCards").children[index].children[0];
          if (er != null)
            document
              .getElementById("rootCards")
              .children[index].children[0].classList.remove("selectedCard");
      }
      if (reverse) {
        document
          .getElementById("rootCards")
          .children[this.currentIdx].children[0].classList.add("selectedCard");
        document
          .getElementById("rootCards")
          .children[this.currentIdx].children[0].classList.remove("card");
      } else {
        document
          .getElementById("rootCards")
          .children[this.currentIdx].children[0].classList.remove(
            "selectedCard"
          );
        document
          .getElementById("rootCards")
          .children[this.currentIdx].children[0].classList.add("card");
      }
    },
  },
};
</script>

<style>
.card {
  margin: 5px;
  background-color: white !important;
}
.selectedCard {
  margin: 5px;
  background-color: var(--soft-blue-color) !important;
}
.present-carousel {
  padding-top: 5px;
}
.present-carousel .v-window__next {
  background: rgba(0, 0, 0, 0.15);
  height: 35px;
  width: 35px;
}
.present-carousel .v-window__prev {
  background: rgba(0, 0, 0, 0.15);
  height: 35px;
  width: 35px;
  top: calc(50% - 20px);
}
.modal-img {
  height: 35px;
  width: 35px;
}
</style>
