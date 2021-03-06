package com.unpeu.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessagePostReq {
	@ApiModelProperty(name = "user_id", example = "1")
	private Long userId;
	@ApiModelProperty(name = "sender", example = "guest")
	private String sender;
	@ApiModelProperty(name = "content", example = "Happy Adult's Day!")
	private String content;
	@ApiModelProperty(name = "category", example = "2022_어른이날")
	private String category;
	@ApiModelProperty(name = "price", example = "10000")
	private int price;
	@ApiModelProperty(name = "create_at")
	private LocalDateTime createdAt;
	@ApiModelProperty(name = "present_id", example = "1")
	private Long presentId;
	@ApiModelProperty(name = "present_img")
	private String presentImg;

}
