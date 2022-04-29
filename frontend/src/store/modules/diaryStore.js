import * as diaryApi from "@/api/diary";

export const diaryStore = {
  namespaced: true,

  state: {
    categoryList: [],
    boardList: [],
    boardInfo: {},
    comment: {},
  },

  getters: {
    GET_CATEGORY_LIST(state) {
      return state.categoryList;
    },

    GET_BOARD_LIST(state) {
      return state.boardList;
    },

    GET_BOARD_DETAIL(state) {
      return state.boardInfo;
    },
  },

  mutations: {
    MU_CATEGORY_LIST(state, categoryList) {
      console.log("category mutation");
      state.categoryList = categoryList;
    },

    MU_BOARD_LIST(state, boardList) {
      console.log("boardList mutation");
      state.boardList = boardList;
    },

    MU_BOARD_DETAIL(state, boardInfo) {
      console.log("boardInfo mutation");
      state.boardInfo = boardInfo;
    },
  },

  actions: {
    /* 카테고리 목록 조회 */
    AC_CATEGORY_LIST({ commit }, userId) {
      diaryApi.getCategory(
        userId,
        (res) => {
          console.log("categoryList action");
          if (res.status == 200) {
            console.log("카테고리 목록 조회 성공");
            commit("MU_CATEGORY_LIST", res.data.category);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    /* 게시글 목록 조회 */
    AC_BOARD_LIST({ commit }, value) {
      diaryApi.getBoardList(
        value.userId,
        value.category,
        (res) => {
          console.log("boardList action");
          if (res.status == 200) {
            console.log("게시글 목록 조회 성공");
            commit("MU_BOARD_LIST", res.data.boardList);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    /* 게시글 상세 조회 */
    AC_BOARD_DETAIL({ commit }, boardId) {
      diaryApi.getBoardInfo(
        boardId,
        (res) => {
          if (res.status == 200) {
            console.log("게시글 상세 조회 성공");
            commit("MU_BOARD_DETAIL", res.data.boardInfo);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    /* 게시글 등록 */
    AC_REGISTER_BOARD({ commit }, boardInfo) {
      diaryApi.registerBoard(
        boardInfo,
        (res) => {
          console.log("save board action");

          var message = "저장을 실패하였습니다.";
          if (res.status == 200) {
            message = "저장을 성공하였습니다.";
            this.$router.push({ name: "BoardList" });
          }
          alert(message);
          commit;
        },
        (error) => {
          console.log(error);
        }
      );
    },

    /* 게시글 수정 */
    AC_EDIT_BOARD({ commit }, value) {
      diaryApi.editBoard(
        value.boardId,
        value.boardInfo,
        (res) => {
          console.log("edit board action");

          var message = "수정을 실패하였습니다.";
          if (res.status == 200) {
            message = "수정을 성공했습니다.";
          }
          alert(message);
          commit;
        },
        (error) => {
          console.log(error);
        }
      );
    },

    /* 게시글 삭제 */
    AC_DELETE_BOARD({ commit }, boardId) {
      diaryApi.deleteBoard(
        boardId,
        (res) => {
          console.log("delete board action");

          var message = "삭제를 실패하였습니다.";
          if (res.status == 200) {
            message = "삭제가 완료되었습니다.";
          }
          alert(message);
          commit;
        },
        (error) => {
          console.log(error);
        }
      );
    },

    /* ====================== Comment ====================== */

    /* 댓글 등록 */
    ACT_REGISTER_COMMENT({ commit }, value) {
      diaryApi.registerComment(
        value.boardId,
        value.commentInfo,
        (res) => {
          console.log("register comment action");

          var message = "등록을 실패하였습니다.";
          if (res.status == 200) {
            message = "등록을 성공하였습니다.";
          }
          alert(message);
          commit;
        },
        (error) => {
          console.log(error);
        }
      );
    },

    /* 댓글 수정 */
    ACT_EDIT_COMMENT({ commit }, value) {
      diaryApi.editComment(
        value.commentId,
        value.commentInfo,
        (res) => {
          console.log("edit comment action");
          if (res.status == 200) {
            console.log("댓글 수정 완료");
            commit;
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    /* 댓글 삭제 */
    ACT_DELETE_COMMENT({ commit }, value) {
      diaryApi.deleteComment(
        value.commentId,
        value.password,
        (res) => {
          console.log("delete comment action");
          var message = "삭제를 실패하였습니다.";
          if (res.status == 200) {
            message = "삭제가 완료되었습니다.";
          }
          alert(message);
          commit;
        },
        (error) => {
          // 백엔드 실패 status 확인 필요
          alert("삭제를 실패하였습니다.");
          console.log(error);
        }
      );
    },
  },
};
