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
	Present createPresent(PresentPostReq present);
	void deletePresent(Long presentId);
	Present updatePresent(Long presentId, PresentPostReq present);
	List<Present> getPresentListByUserId(Long userId);
	Message sendMessageAndPresent(MessagePostReq message);
	String peekMoney(Long userId);
}
