package com.unpeu.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unpeu.domain.entity.Message;
import com.unpeu.domain.repository.IMessageRepository;
import com.unpeu.service.iface.IMessageService;

import lombok.RequiredArgsConstructor;

/**
 * IMessageService 를 implements 하여 구현합니다.
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements IMessageService {
	private final IMessageRepository messageRepository;

	/**
	 * userId에 따라서 MessageList 전체 조회
	 * @param userId
	 * @return
	 */
	@Override
	public List<Message> getMessageListByUserId(Long userId) {
		return messageRepository.findMessageByUser_Id(userId);
	}
}
