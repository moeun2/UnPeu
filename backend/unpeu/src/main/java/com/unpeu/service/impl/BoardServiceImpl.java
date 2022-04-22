package com.unpeu.service.impl;

import com.unpeu.domain.entity.Board;
import com.unpeu.domain.entity.User;
import com.unpeu.domain.repository.IBoardRepository;
import com.unpeu.domain.request.BoardPostReq;
import com.unpeu.domain.response.BoardGetRes;
import com.unpeu.service.iface.IBoardService;
import com.unpeu.service.iface.IUserService;
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
    private final IUserService userService;

    /**
     * userId에 맞는 category 리스트 조회
     * @param userId
     * @return
     */
    @Override
    public List<String> getCategoryList(Long userId) {
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
    public BoardGetRes getBoardInfo(Long boardId) {
        Optional<Board> oBoard = boardRepository.findById(boardId);
        return new BoardGetRes(oBoard.get());
    }

    /**
     * user의 다이어리에 게시글 등록
     * @param user
     * @param board
     * @return
     */
    @Override
    public Board createBoard(User user, BoardPostReq board) {
        Board newboard = Board.builder()
                .title(board.getTitle())
                .content(board.getContents())
                .category(board.getCategory())
                .build();

        newboard.setUser(user);
        return boardRepository.save(newboard);
    }

    /**
     * boardId에 맞는 게시글 수정
     * @param boardId
     * @param board
     * @return
     */
    @Override
    public boolean updateBoard(Long boardId, BoardPostReq board) {
        Optional<Board> oBoard = boardRepository.findById(boardId);

        if (oBoard.isEmpty()) {
            throw new NoSuchElementException("boardId가" + boardId + " 인 게시글을 찾을 수 없습니다");
        }

        Board prevBoard = oBoard.get();

        if(board.getCategory() != null && board.getTitle() != null && board.getContents() != null)
            prevBoard.updateBoardInfo(board.getCategory(), board.getTitle(), board.getContents());
        Board updateBoard = boardRepository.save(prevBoard);

        return true;
    }

    /**
     * boardId에 맞는 게시글 삭제
     * @param boardId
     * @return
     */
    @Override
    public boolean deleteBoard(Long boardId) {
        Optional<Board> oBoard = boardRepository.findById(boardId);

        if(oBoard.isEmpty()) {
            throw new NoSuchElementException("boardId가 " + boardId + " 인 게시글을 찾을 수 없습니다.");
        }

        boardRepository.delete(oBoard.get());
        return true;
    }
}
