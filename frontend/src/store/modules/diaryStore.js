import * as diaryApi from "@/api/diary";
import router from "../../router"; 
import Swal from 'sweetalert2'

export const diaryStore = {
  namespaced: true,

  state: {
    categoryList: [],
    boardList: [],
    boardInfo: {},
    commentInfo: {},
    editCommentFlag: false, // 댓글 수정 여부 체크
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

    GET_COMMENT_DETAIL(state) {
      return state.commentInfo;
    },

    GET_EDIT_FLAG(state) {
      return state.editCommentFlag;
    },
  },

  mutations: {
    MU_CATEGORY_LIST(state, categoryList) {
      // console.log("category mutation");
      state.categoryList = categoryList;
    },

    MU_BOARD_LIST(state, boardList) {
      // console.log("boardList mutation");
      state.boardList = boardList;
    },

    MU_BOARD_DETAIL(state, boardInfo) {
      // console.log("boardInfo mutation");
      state.boardInfo = boardInfo;
    },

    MU_COMMENT_DETAIL(state, commentInfo) {
      // console.log("commentInfo mutation");
      state.commentInfo = commentInfo;
    },

    MU_EDIT_FLAG(state, editFlag) {
      // console.log("editFlag mutation");
      state.editCommentFlag = editFlag;
    },
  },

  actions: {
    /* 카테고리 목록 조회 */
    AC_CATEGORY_LIST({ commit }, userId) {
      diaryApi.getCategory(
        userId,
        (res) => {
          // console.log("categoryList action");
          // console.log("카테고리 목록 조회 성공");
          commit("MU_CATEGORY_LIST", res.data.category);
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
          // console.log("boardList action");
          // console.log("게시글 목록 조회 성공");
          commit("MU_BOARD_LIST", res.data.boardList);
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
          // console.log("게시글 상세 조회 성공");
          commit("MU_BOARD_DETAIL", res.data.boardInfo);
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
        () => {
          // console.log("save board action");
          // console.log(res.data);
          commit;

          router.replace({ name: "BoardList" });
          // alert("저장을 성공하였습니다.");
        },
        (error) => {
          Swal.fire(
            'Save',
            '저장을 실패하였습니다.',
            'error'
          )
          // alert("저장을 실패하였습니다.");
          console.log(error);
        }
      );
    },

    /* 게시글 수정 */
    AC_EDIT_BOARD({ commit }, value) {
      diaryApi.editBoard(
        value.boardId,
        value.boardInfo,
        () => {
          // console.log("edit board action");
          // console.log(res.data);
          commit;

          router.replace({
            name: "BoardDetail",
            params: { boardId: value.boardId },
          });
          // alert("수정을 성공했습니다.");
        },
        (error) => {
          Swal.fire(
            'Update',
            '수정을 실패했습니다.',
            'error'
          )
          console.log(error);
        }
      );
    },

    /* 게시글 삭제 */
    AC_DELETE_BOARD({ commit }, boardId) {
      diaryApi.deleteBoard(
        boardId,
        () => {
          // console.log("delete board action");
          // console.log(res.data);
          // alert("삭제가 완료되었습니다.");
          Swal.fire(
            'Delete',
            '삭제가 완료되었습니다.',
            'success'
          )
          commit;
          router.push({ name: "BoardList" });
        },
        (error) => {
          Swal.fire(
            'Delete',
            '삭제가 실패하였습니다.',
            'error'
          )
          // alert("삭제가 실패하였습니다.");
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
        () => {
          // console.log("register comment action");
          // console.log(res.data);
          // console.log("댓글 등록을 성공하였습니다.");
          commit("MU_COMMENT_DETAIL", value.commentInfo);
        },
        (error) => {
          // console.log("등록을 실패하였습니다.");
          console.log(error);
        }
      );
    },

    /* 댓글 수정 */
    ACT_EDIT_COMMENT({ commit }, value) {
      diaryApi.editComment(
        value.commentId,
        value.commentInfo,
        () => {
          // console.log("edit comment action");
          // console.log(res.data);
          Swal.fire(
            'Update',
            '댓글 수정이 완료되었습니다.',
            'success'
          )
          commit;
        },
        (error) => {
          Swal.fire(
            'Update',
            '댓글 수정을 실패하였습니다.',
            'error'
          )
          // alert("댓글 수정을 실패하였습니다.");
          console.log(error);
        }
      );
    },

    /* 댓글 삭제 */
    ACT_DELETE_COMMENT({ commit }, value) {
      diaryApi.deleteComment(
        value.commentId,
        value.password,
        () => {
          // console.log("delete comment action");
          // console.log(res.data);
          Swal.fire(
            'Delete',
            '댓글 삭제가 완료되었습니다.',
            'success'
          )
          commit;
        },
        (error) => {
          Swal.fire(
            'Delete',
            '댓글 삭제를 실패하였습니다.',
            'error'
          )
          // alert("댓글 삭제를 실패하였습니다.");
          console.log(error);
        }
      );
    },

    /* 댓글 수정 요청 시 플래그와 댓글 정보 값 변경 */
    ACT_SETTING_COMMENT({ commit }, value) {
      commit("MU_EDIT_FLAG", value.editFlag);

      if (value.commentInfo) {
        commit("MU_COMMENT_DETAIL", value.commentInfo);
      }
    },
  },
};
