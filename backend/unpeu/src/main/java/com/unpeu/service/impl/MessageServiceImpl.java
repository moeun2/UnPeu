package com.unpeu.service.impl;

import static com.unpeu.config.exception.ErrorCode.*;

import java.util.List;
import java.util.Optional;

import com.unpeu.domain.entity.Board;
import com.unpeu.domain.repository.IBoardRepository;
import com.unpeu.domain.request.MessagePostReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unpeu.config.exception.CustomException;
import com.unpeu.domain.entity.Message;
import com.unpeu.domain.entity.User;
import com.unpeu.domain.repository.IMessageRepository;
import com.unpeu.domain.repository.IPresentRepository;
import com.unpeu.domain.repository.IUserRepository;
import com.unpeu.service.iface.IMessageService;

import lombok.RequiredArgsConstructor;

/**
 * IMessageService 를 implements 하여 구현합니다.
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements IMessageService {
	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

	private final IMessageRepository messageRepository;
	private final IPresentRepository presentRepository;
	private final IBoardRepository boardRepository;
	private final IUserRepository userRepository;
	/**
	 * userId에 따라서 MessageList 전체 조회
	 * @param userId
	 * @return
	 */
	@Override
	public List<Message> getMessageListByUserId(Long userId) {
		logger.info("getMessageListByUserId-호출");
		Optional<User> user = userRepository.findById(userId);
		if (user.isEmpty()) {
			throw new CustomException(MEMBER_NOT_FOUND);
		}
		return messageRepository.findMessageByUserId(userId);
	}

	/**
	 * messageId에 따라 message 상세 조회
	 * @param messageId
	 * @return
	 */
	@Override
	public Message getMessage(Long messageId) {
		logger.info("getMessage-호출");
		return messageRepository.findById(messageId).get();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteAllMessageByUserId(long userId) {
		logger.info("deleteAllMessageByUserId-호출");
		try {
			presentRepository.deleteAllByUserId(userId);
			messageRepository.deleteAllByUserId(userId);
		} catch (Exception e) {
			throw new CustomException(DELETE_CONFLICT);
		}

	}

	/**
	 * message List를 받아와서 다이어리에 저장
	 * @param messages
	 * @return
	 */
	@Override
	public void saveMessage(List<MessagePostReq> messages) {
		logger.info("saveMessage-호출");
		Optional<User> user = userRepository.findById(Long.parseLong(/*message.getUserId()*/"1"));
		if (user.isEmpty()) {
			throw new CustomException(MEMBER_NOT_FOUND);
		}

		for(MessagePostReq message : messages) {
			Board newBoard = Board.saveMessage()
					.user(user.get())
					.category(message.getCategory())
					.title(message.getSender())
					.content(message.getContent() + " (" + message.getPrice() + "원 펀딩)")
					.createdAt(message.getCreatedAt())
					.build();
			boardRepository.save(newBoard);
			// present 이미지 추가 해야 함.
		}
	}
}
