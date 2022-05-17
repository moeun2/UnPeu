package com.unpeu.service.iface;

import java.util.List;

import com.unpeu.domain.entity.User;

import com.unpeu.domain.entity.Message;
import com.unpeu.domain.request.MessagePostReq;
import com.unpeu.domain.response.MessageGetRes;

public interface IMessageService {
	List<MessageGetRes> getMessageListByUserId(Long userId);
	Message getMessage(Long messageId);
	void deleteAllMessageByUserId(User user);
	void saveMessage(User user, List<MessagePostReq> messages);
}
