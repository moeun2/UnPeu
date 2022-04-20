package com.unpeu.domain.request;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PresentPostReq {
	@ApiModelProperty(name = "presentName", example = "앙꼬인형")
	private String presentName;

	@ApiModelProperty(name = "presentImg", example = "첨부파일 img")
	private MultipartFile presentImg;

	@ApiModelProperty(name = "presentImgUrl", example = "첨부파일 img url")
	private String presentImgUrl;

	@ApiModelProperty(name = "presentPrice", example = "30000")
	private String presentPrice;

	@ApiModelProperty(name = "receivedPrice", example = "10000")
	private String receivedPrice;

	// AccessToken 받으면 없어질 항목
	@ApiModelProperty(name = "userId", example = "user_id")
	private String userId;
}
