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
	@Transactional
	Present createPresent(PresentPostReq present);

	@Transactional
	void deletePresent(Long presentId);

	@Transactional
	Present updatePresent(Long presentId, PresentPostReq present);

	@Transactional
	List<Present> getPresentListByUserId(Long userId);

	@Transactional
	Message sendMessageAndPresent(MessagePostReq message);

	String peekMoney(Long userId);
}
