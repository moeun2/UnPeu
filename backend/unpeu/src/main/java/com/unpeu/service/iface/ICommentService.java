package com.unpeu.service.iface;

import com.unpeu.domain.request.CommentPostReq;

public interface ICommentService {

    void createComment(Long boardId, CommentPostReq comment);

    void updateComment(Long commentId, CommentPostReq comment);

    void deleteComment(Long commentId, String password);
}
