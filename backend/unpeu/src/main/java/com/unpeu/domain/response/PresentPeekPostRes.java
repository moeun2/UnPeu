package com.unpeu.domain.response;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PresentPeekRes")
public class PresentPeekPostRes extends BaseResponseBody{
	String money;

	public static PresentPeekPostRes of(Integer statusCode, String message, String money) {
		PresentPeekPostRes res = new PresentPeekPostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setMoney(money);
		return res;
	}
}
