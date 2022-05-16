package com.unpeu.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPatchUserMusicReq {
	
	@ApiModelProperty(name = "userMusic", example = "happy")
    private String userMusic;

    public UserPatchUserMusicReq(String userMusic) {
        this.userMusic = userMusic;
    }

}
