import { createInstance, tokenInstance } from "./index.js";

const api = createInstance();
const tokenApi = tokenInstance();

/* ====================== Baord ====================== */

// 게시글 목록 전체 조회
function getBoardList(userId, category, success, fail) {
  api.get(`board/${userId}/${category}`).then(success).catch(fail);
}

// 카테고리 조회
function getCategory(userId, success, fail) {
  api.get(`board/category/${userId}`).then(success).catch(fail);
}

// 게시글 상세 조회
function getBoardInfo(boardId, success, fail) {
  api.get(`board/${boardId}`).then(success).catch(fail);
}

// 게시글 등록
function registerBoard(boardInfo, success, fail) {
  tokenApi.post(`board`, boardInfo).then(success).catch(fail);
}

// 게시글 수정
function editBoard(boardId, boardInfo, success, fail) {
  tokenApi.put(`board/${boardId}`, boardInfo).then(success).catch(fail);
}

// 게시글 삭제
function deleteBoard(boardId, success, fail) {
  tokenApi.delete(`board/${boardId}`).then(success).catch(fail);
}

/* ====================== Comment ====================== */

// 댓글 등록
function registerComment(boardId, comment, success, fail) {
  api.post(`comment/${boardId}`, comment).then(success).catch(fail);
}

// 댓글 수정
function editComment(commentId, comment, success, fail) {
  api.put(`comment/${commentId}`, comment).then(success).catch(fail);
}

// 댓글 삭제
function deleteComment(commentId, password, success, fail) {
  api.delete(`comment/${commentId}/${password}`).then(success).catch(fail);
}

export {
  getBoardList,
  getCategory,
  getBoardInfo,
  registerBoard,
  editBoard,
  deleteBoard,
  registerComment,
  editComment,
  deleteComment,
};
