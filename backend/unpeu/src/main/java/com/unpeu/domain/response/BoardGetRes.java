package com.unpeu.domain.response;

import com.unpeu.domain.entity.Board;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@ApiModel("BoardGetRes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardGetRes {
    private Long userId;
    private Long boardId;
    private String category;
    private String createdAt;

    public BoardGetRes(Board board) {
        this.userId = board.getUser().getId();
        this.boardId = board.getBoardId();
        this.category = board.getCategory();
        this.createdAt = board.getCreatedAt();
    }

}
