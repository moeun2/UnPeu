package com.unpeu.domain.response;

import com.unpeu.domain.entity.Board;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ApiModel("BoardGetRes")
public class BoardGetRes {
    private Long userId;
    private Long boardId;
    private String title;
    private String contents;
    private String category;
    private LocalDateTime createdAt;

//    private List<Comment> commentList;

    public BoardGetRes(Board board) {
        this.userId = board.getUser().getId();
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.contents = board.getContent();
        this.category = board.getCategory();
        this.createdAt = board.getCreateAt();
    }

}
