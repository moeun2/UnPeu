package com.unpeu.domain.request;

import java.time.LocalDate;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPatchConceptReq {
	@ApiModelProperty(name = "userId", example = "1")
	private Long userId;

	@ApiModelProperty(name = "category", example = "birthday")
    private String category;

	@ApiModelProperty(name = "selectedDate", example = "1997-12-09")
    private LocalDate selectedDate;

    public UserPatchConceptReq(Long userId,String category,LocalDate selectedDate) {
		this.userId = userId;
        this.category = category;
        this.selectedDate = selectedDate;
    }

}
