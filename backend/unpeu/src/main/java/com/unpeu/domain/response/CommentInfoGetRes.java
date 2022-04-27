package com.unpeu.domain.response;

import com.unpeu.domain.entity.Comment;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@ApiModel("CommentInfoGetRes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentInfoGetRes {

    private Long commentId;
    private String writer;
    private String password;
    private String content;
    private String createdAt;

    public CommentInfoGetRes(Comment comment) {
        this.commentId = comment.getCommentId();
        this.writer = comment.getWriter();
        this.password = comment.getPassword();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
