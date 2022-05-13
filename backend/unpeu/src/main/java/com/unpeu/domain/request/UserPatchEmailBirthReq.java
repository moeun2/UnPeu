package com.unpeu.domain.request;

import java.time.LocalDate;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPatchEmailBirthReq {
	
	@ApiModelProperty(name = "userEmail", example = "ssafy123@ssafy.com")
    private String userEmail;
	
	@ApiModelProperty(name = "userBirth", example = "1997-12-09")
    private LocalDate userBirth;

    public UserPatchEmailBirthReq(String userEmail,LocalDate userBirth) {
        this.userEmail = userEmail;
        this.userBirth = userBirth;
        
    }

}
