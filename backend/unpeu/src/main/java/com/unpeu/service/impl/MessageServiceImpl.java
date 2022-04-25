package com.unpeu.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unpeu.domain.entity.Message;
import com.unpeu.domain.repository.IMessageRepository;
import com.unpeu.domain.repository.IPresentRepository;
import com.unpeu.service.iface.IMessageService;

import lombok.RequiredArgsConstructor;

/**
 * IMessageService 를 implements 하여 구현합니다.
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements IMessageService {
	private final IMessageRepository messageRepository;
	private final IPresentRepository presentRepository;

	/**
	 * userId에 따라서 MessageList 전체 조회
	 * @param userId
	 * @return
	 */
	@Override
	public List<Message> getMessageListByUserId(Long userId) {
		return messageRepository.findMessageByUserId(userId);
	}

	/**
	 * messageId에 따라 message 상세 조회
	 * @param messageId
	 * @return
	 */
	@Override
	public Message getMessage(Long messageId) {
		return messageRepository.findById(messageId).get();
	}

	@Override
	public void deleteAllMessageByUserId(long userId) {
		presentRepository.deleteAllByUserId(userId);
		messageRepository.deleteAllByUserId(userId);
	}
}
