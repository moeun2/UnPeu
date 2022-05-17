package com.unpeu.service.impl;

import static com.unpeu.config.exception.ErrorCode.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unpeu.config.exception.CustomException;
import com.unpeu.domain.entity.Message;
import com.unpeu.domain.entity.Present;
import com.unpeu.domain.entity.User;
import com.unpeu.domain.repository.IMessageRepository;
import com.unpeu.domain.repository.IPresentRepository;
import com.unpeu.domain.repository.IUserRepository;
import com.unpeu.domain.request.MessagePostReq;
import com.unpeu.domain.request.PresentPostReq;
import com.unpeu.service.iface.IPresentService;

import lombok.RequiredArgsConstructor;

/**
 * IPresentServie를 implements 하여 구현합니다.
 */
@Service
@RequiredArgsConstructor
public class PresentServiceImpl implements IPresentService {
	private static final Logger logger = LoggerFactory.getLogger(PresentServiceImpl.class);
	
	private final IPresentRepository presentRepository;
	private final IUserRepository userRepository;
	private final IMessageRepository messageRepository;

	/**
	 * 선물리스트에 선물 등록
	 * @param present
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Present createPresent(PresentPostReq present) {
		logger.info("createPreset-호출");
		Optional<User> user = userRepository.findById(Long.parseLong(present.getUserId()));
		if (user.isEmpty()) {
			throw new CustomException(MEMBER_NOT_FOUND);
		}

		Present newPresent = Present.builder()
			.user(user.get())
			.presentImg(present.getPresentImgUrl())
			.presentName(present.getPresentName())
			.presentPrice(present.getPresentPrice())
			.receivedPrice(0)
			.build();
		return presentRepository.save(newPresent);
	}

	/**
	 * presentId에 따라 선물리스트에서 선물 삭제
	 * @param presentId
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deletePresent(Long presentId) {
		logger.info("deletePresent-호출");
		// 해당 선물에 이미 메세지(선물 or 돈)을 받았다면
		Optional<Present> oPresent = presentRepository.findById(presentId);
		if (oPresent.isEmpty()) {
			throw new CustomException(PRESENT_NOT_FOUND);
		}
		Optional<Message> oMessage = messageRepository.findFirstByPresent(oPresent.get());
		if (!oMessage.isEmpty()) {
			System.out.println(oMessage.get());
			throw new CustomException(DUPLICATE_RESOURCE);
		}
		try {
			presentRepository.deleteById(presentId);
		} catch (Exception e) {
			throw new CustomException(MEMBER_NOT_FOUND);
		}
	}

	/**
	 * presentId에 맞는 present 수정
	 * @param presentId
	 * @param present
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Present updatePresent(Long presentId, PresentPostReq present) {
		logger.info("updatePresent-호출");
		Optional<Present> oPresent = presentRepository.findById(presentId);
		if (oPresent.isEmpty()) {
			throw new CustomException(PRESENT_NOT_FOUND);
		}
		Present prevPresent = oPresent.get();

		if (present.getPresentName() != null) // presentName 수정
			prevPresent.setPresentName(present.getPresentName());
		if (present.getPresentPrice() != null) // presentPrice 수정
			prevPresent.setPresentPrice(present.getPresentPrice());
		if (present.getPresentImgUrl() != null) // Image 수정
			prevPresent.setPresentImg(present.getPresentImgUrl());

		return presentRepository.save(prevPresent);
	}

	/**
	 * userId에 따라 선물 리스트 전체 조회
	 * @param userId
	 * @return
	 */
	@Override
	public List<Present> getPresentListByUserId(Long userId) {
		logger.info("getPresentListByUserId-호출");
		Optional<User> user = userRepository.findById(userId);
		if (user.isEmpty()) {
			throw new CustomException(MEMBER_NOT_FOUND);
		}
		return presentRepository.findPresentByUserId(userId);
	}

	/**
	 * Message(메세지와 선물) 내역 기록
	 * @param message
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Message sendMessageAndPresent(MessagePostReq message) {
		logger.info("sendMessageAndPresent-호출");
		Present present = null;
		if (message.getPresentId() != null) {
			Optional<Present> oPresent = presentRepository.findById(message.getPresentId());
			if (oPresent.isEmpty()) {
				throw new CustomException(PRESENT_NOT_FOUND);
			}
			present = oPresent.get();
			present.setReceivedPrice(present.getReceivedPrice() + message.getPrice());
			presentRepository.save(present);
		}

		Optional<User> user = userRepository.findById(message.getUserId());
		if (user.isEmpty()) {
			throw new CustomException(MEMBER_NOT_FOUND);
		}

		Message newMessage = Message.builder()
			.user(user.get())
			.sender(message.getSender())
			.content(message.getContent())
			.category(message.getCategory())
			.price(message.getPrice())
			.createdAt(LocalDateTime.now())
			.present(present)
			.build();
		return messageRepository.save(newMessage);
	}

	/**
	 * user가 지금까지 받은 돈을 엿볼 수 있다.
	 * @return
	 */
	@Override
	public String peekMoney(Long userId) {
		logger.info("peekMoney-호출");
		Optional<User> user = userRepository.findById(userId);
		if (user.isEmpty()) {
			throw new CustomException(MEMBER_NOT_FOUND);
		}
		Optional<Message> oMessage = messageRepository.findFirstByUserId(userId);
		if (oMessage.isEmpty()) {
			throw new CustomException(MESSAGE_NOT_FOUND_BY_USER);
		}
		return messageRepository.sumPeekMoney(userId);
	}
}
