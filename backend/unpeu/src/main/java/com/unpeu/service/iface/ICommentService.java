package com.unpeu.service.iface;

import com.unpeu.domain.request.CommentPostReq;
import org.springframework.transaction.annotation.Transactional;

public interface ICommentService {

    @Transactional(rollbackFor = Exception.class)
    void createComment(Long boardId, CommentPostReq comment);

    @Transactional(rollbackFor = Exception.class)
    void updateComment(Long commentId, CommentPostReq comment);

    @Transactional(rollbackFor = Exception.class)
    void deleteComment(Long commentId, String password);
}
