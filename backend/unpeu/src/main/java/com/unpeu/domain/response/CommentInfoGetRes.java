package com.unpeu.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unpeu.domain.entity.Comment;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@ApiModel("CommentInfoGetRes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentInfoGetRes {

    private Long commentId;
    private String writer;
    private String password;
    private String content;

    @JsonFormat(pattern = "YYYY.MM.dd HH:mm:ss")
    private LocalDateTime createdAt;

    public CommentInfoGetRes(Comment comment) {
        this.commentId = comment.getCommentId();
        this.writer = comment.getWriter();
        this.password = comment.getPassword();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
