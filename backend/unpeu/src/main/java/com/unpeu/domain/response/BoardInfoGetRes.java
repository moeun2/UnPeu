package com.unpeu.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.unpeu.domain.entity.Board;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ApiModel("BoardInfoGetRes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardInfoGetRes {
    private Long userId;
    private Long boardId;
    private String title;
    private String content;
    private String category;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private List<CommentInfoGetRes> commentList;

    public BoardInfoGetRes(Board board) {
        this.userId = board.getUser().getId();
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.category = board.getCategory();
        this.createdAt = board.getCreatedAt();
        this.commentList = board.getComments().stream().map(CommentInfoGetRes::new).collect(Collectors.toList());
    }
}
