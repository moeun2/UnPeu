package com.unpeu.service.impl;

import com.unpeu.config.exception.ApplicationException;
import com.unpeu.domain.entity.Board;
import com.unpeu.domain.entity.User;
import com.unpeu.domain.repository.IBoardRepository;
import com.unpeu.domain.request.BoardPostReq;
import com.unpeu.domain.response.BoardGetRes;
import com.unpeu.domain.response.BoardInfoGetRes;
import com.unpeu.service.iface.IBoardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * BoardService를 implements하여 구현합니다.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements IBoardService {
    private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

    private final IBoardRepository boardRepository;

    /**
     * userId에 맞는 category 리스트 조회
     * @param userId
     * @return
     */
    @Override
    public List<String> getCategoryList(Long userId) {
        logger.info("getCategoryList - 호출");
        List<String> categories = boardRepository.findCategoryByUserId(userId);
        return categories;
    }

    /**
     * userId에 맞는 category별 게시글 전체 조회
     * @param userId
     * @param category
     * @return
     */
    @Override
    public List<BoardGetRes> getBoardList(Long userId, String category) {
        logger.info("getBoardList - 호출");
        List<Board> boards = boardRepository.findByUserIdAndCategory(userId, category);
        return boards.stream()
                .map(BoardGetRes::new)
                .collect(Collectors.toList());
    }

    /**
     * boardId에 맞는 게시글 상세 조회
     * @param boardId
     * @return
     */
    @Override
    public BoardInfoGetRes getBoardInfo(Long boardId) {
        logger.info("getBoardInfo - 호출");
        Optional<Board> oBoard = boardRepository.findById(boardId);
        return new BoardInfoGetRes(oBoard.get());
    }

    /**
     * user의 다이어리에 게시글 등록
     * @param user
     * @param board
     * @return
     */
    @Override
    public Board createBoard(User user, BoardPostReq board) {
        logger.info("createBoard - 호출");
        Board newboard = Board.builder()
                .user(user)
                .title(board.getTitle())
                .content(board.getContent())
                .category(board.getCategory())
                .build();

        return boardRepository.save(newboard);
    }

    /**
     * boardId에 맞는 게시글 수정
     * @param boardId
     * @param board
     * @return
     */
    @Override
    public void updateBoard(User user, Long boardId, BoardPostReq board) {
        logger.info("updateBoard - 호출");
        Optional<Board> oBoard = boardRepository.findById(boardId);

        if (oBoard.isEmpty()) {
            throw new NoSuchElementException("boardId가" + boardId + " 인 게시글을 찾을 수 없습니다");
        }

        if(!user.getId().equals(oBoard.get().getUser().getId())) {
            throw new ApplicationException("작성한 유저 " + oBoard.get().getUser().getUserLogin() + "와 요청한 유저 "
                    + user.getUserLogin() + "가 서로 다르기 때문에 수정할 수 없습니다.");
        }

        Board prevBoard = oBoard.get();

        if(board.getCategory() != null && board.getTitle() != null && board.getContent() != null)
            prevBoard.updateBoardInfo(board.getCategory(), board.getTitle(), board.getContent());

        Board updateBoard = boardRepository.save(prevBoard);
    }

    /**
     * boardId에 맞는 게시글 삭제
     * @param boardId
     * @return
     */
    @Override
    public void deleteBoard(User user, Long boardId) {
        logger.info("deleteBoard - 호출");
        Optional<Board> oBoard = boardRepository.findById(boardId);

        if(oBoard.isEmpty()) {
            throw new NoSuchElementException("boardId가 " + boardId + " 인 게시글을 찾을 수 없습니다.");
        }

        if(!user.getId().equals(oBoard.get().getUser().getId())) {
            throw new ApplicationException("작성한 유저 " + oBoard.get().getUser().getUserLogin() + "와 요청한 유저 "
                    + user.getUserLogin() + "가 서로 다르기 때문에 삭제할 수 없습니다.");
        }

        boardRepository.delete(oBoard.get());
    }
}
