package com.unpeu.service.impl;

import com.unpeu.api.diary.CommentController;
import com.unpeu.config.exception.CustomException;
import com.unpeu.domain.entity.Board;
import com.unpeu.domain.entity.Comment;
import com.unpeu.domain.repository.IBoardRepository;
import com.unpeu.domain.repository.ICommentRepository;
import com.unpeu.domain.request.CommentPostReq;
import com.unpeu.service.iface.ICommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.unpeu.config.exception.ErrorCode.*;

/**
 * ICommentService를 implements하여 구현합니다.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private final ICommentRepository commentRepository;
    private final IBoardRepository boardRepository;

    /**
     * boardId에 맞는 게시글에 댓글 등록
     * @param boardId
     * @param comment
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createComment(Long boardId, CommentPostReq comment) {
        logger.info("createComment - 호출");
        Optional<Board> oBoard = boardRepository.findById(boardId);
        if (oBoard.isEmpty()) {
            throw new CustomException(BOARD_NOT_FOUNT);
        }

        Comment newComment = Comment.builder()
                .writer(comment.getWriter())
                .password(comment.getPassword())
                .content(comment.getContent())
                .build();
        newComment.setBoard(oBoard.get());
        commentRepository.save(newComment);
    }

    /**
     * commentId에 맞는 댓글 비밀번호 확인 후 수정
     * @param commentId
     * @param comment
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateComment(Long commentId, CommentPostReq comment) {
        logger.info("updateComment - 호출");
        Optional<Comment> oComment = commentRepository.findById(commentId);

        if(oComment.isEmpty()) {
            throw new CustomException(COMMENT_NOT_FOUNT);
        }

        Comment prevComment = oComment.get();

        if(!prevComment.matchPassword(comment.getPassword())) {
            throw new CustomException(NOT_AUTHORITY_UPDATE_COMMENT);
        }

        prevComment.updateContent(comment.getContent());
        commentRepository.save(prevComment);
    }

    /**
     * commentId에 맞는 댓글 비밀번호 확인 후 삭제
     * @param commentId
     * @param password
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long commentId, String password) {
        logger.info("deleteComment - 호출");
        Optional<Comment> oComment = commentRepository.findById(commentId);

        if(oComment.isEmpty()) {
            throw new CustomException(COMMENT_NOT_FOUNT);
        }

        Comment delComment = oComment.get();

        if(!delComment.matchPassword(password)) {
            throw new CustomException(NOT_AUTHORITY_DELETE_COMMENT);
        }

        commentRepository.delete(delComment);
    }
}
