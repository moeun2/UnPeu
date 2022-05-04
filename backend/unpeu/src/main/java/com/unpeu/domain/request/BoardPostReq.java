package com.unpeu.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardPostReq {

    @ApiModelProperty(name = "userId", example = "1")
    private Long userId;

    @ApiModelProperty(name = "title", example = "어린이날? 어른이날!")
    private String title;

    @ApiModelProperty(name = "contents", example = "성인이 된 나도 소중함. 셀프 선물 하기")
    private String content;

    @ApiModelProperty(name = "category", example = "default")
    private String category;

    @Builder
    public BoardPostReq(Long userId, String title, String content, String category) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
