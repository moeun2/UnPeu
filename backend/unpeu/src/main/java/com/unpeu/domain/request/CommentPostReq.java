package com.unpeu.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentPostReq {

    @ApiModelProperty(name = "writer", example = "방문자")
    private String writer;

    @ApiModelProperty(name = "password", example = "1234")
    private String password;

    @ApiModelProperty(name = "content", example = "생일축하해")
    private String content;

    public CommentPostReq(String writer, String password, String content) {
        this.writer = writer;
        this.password = password;
        this.content = content;
    }
}
