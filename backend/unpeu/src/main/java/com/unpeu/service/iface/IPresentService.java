package com.unpeu.service.iface;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.unpeu.domain.entity.Message;
import com.unpeu.domain.entity.Present;
import com.unpeu.domain.entity.User;
import com.unpeu.domain.request.MessagePostReq;
import com.unpeu.domain.request.PresentPostReq;
import com.unpeu.domain.response.BaseResponseBody;

public interface IPresentService {
	@Transactional(rollbackFor = Exception.class)
	Present createPresent(PresentPostReq present);

	@Transactional(rollbackFor = Exception.class)
	void deletePresent(Long presentId);

	@Transactional(rollbackFor = Exception.class)
	Present updatePresent(Long presentId, PresentPostReq present);

	List<Present> getPresentListByUserId(Long userId);

	@Transactional(rollbackFor = Exception.class)
	Message sendMessageAndPresent(MessagePostReq message);

	String peekMoney(Long userId);
}
