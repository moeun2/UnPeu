package com.unpeu.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPatchReq {

    @ApiModelProperty(name = "userInfo", example = "선물주는 사람 \n 차칸 사람")
    private String userInfo;

    public UserPatchReq(String userInfo) {
        this.userInfo = userInfo;
        
    }
}