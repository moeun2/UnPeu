package com.unpeu.service.iface;

import java.util.List;

import com.unpeu.domain.entity.Message;
import com.unpeu.domain.entity.Present;
import com.unpeu.domain.request.MessagePostReq;
import com.unpeu.domain.request.PresentPostReq;

public interface IPresentService {
	Present createPresent(PresentPostReq present);
	void deletePresent(Long presentId);
	Present updatePresent(Long presentId, PresentPostReq present);
	List<Present> getPresentListByUserId(Long userId);
	Message sendMessageAndPresent(MessagePostReq message);
	String peekMoney(Long userId);
}
