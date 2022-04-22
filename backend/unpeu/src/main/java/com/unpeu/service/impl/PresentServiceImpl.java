package com.unpeu.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.unpeu.config.exception.ApplicationException;
import com.unpeu.config.exception.EmptyResultDataAccessException;
import com.unpeu.domain.entity.Message;
import com.unpeu.domain.entity.Present;
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

	// public PresentService(IPresentRepository presentRepository, IUserRepository userRepository,
	// 	IMessageRepository messageRepository) {
	// 	this.presentRepository = presentRepository;
	// 	this.userRepository = userRepository;
	// 	this.messageRepository = messageRepository;
	// }

	/**
	 * 선물리스트에 선물 등록
	 * @param present
	 * @return
	 */
	@Override
	public Present createPresent(PresentPostReq present) {
		// Optional<User> user = userRepository.findById(Long.parseLong(present.getUserId()));
		// if (user.isEmpty()) {
		// 	throw new NoSuchElementException("userId가 " + present.getUserId() + " 인 유저를 찾을 수 없습니다");
		// }

		Present newPresent = Present.builder()
			.user(/*user.get()*/null) //Test시 null로 테스트 했음
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
	public void deletePresent(Long presentId) {
		// 해당 선물에 이미 메세지(선물 or 돈)을 받았다면
		Optional<Present> oPresent = presentRepository.findById(presentId);
		if (oPresent.isEmpty()) {
			throw new NoSuchElementException("presentId가 " + presentId + " 인 선물을 찾을 수 없습니다");
		}
		Optional<Message> oMessage = messageRepository.findFirstByPresent(oPresent.get());
		if (!oMessage.isEmpty()) {
			System.out.println(oMessage.get());
			throw new ApplicationException("presentId가 " + presentId + " 인 선물에 돈/메세지가 이미 들어가 있습니다.");
		}

		try {
			presentRepository.deleteById(presentId);
		} catch (Exception e) {
			throw new EmptyResultDataAccessException("presentId : " + presentId + " 에 맞는 데이터가 없습니다.");
		}
	}

	/**
	 * presentId에 맞는 present 수정
	 * @param presentId
	 * @param present
	 * @return
	 */
	@Override
	public Present updatePresent(Long presentId, PresentPostReq present) {
		Optional<Present> oPresent = presentRepository.findById(presentId);
		if (oPresent.isEmpty()) {
			throw new NoSuchElementException("presentId가 " + presentId + " 인 선물을 찾을 수 없습니다");
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
		// return presentRepository.getPresentListByUserId(userId);
		return presentRepository.findByUser_Id(null);
	}

	/**
	 * Message(메세지와 선물) 내역 기록
	 * @param message
	 * @return
	 */
	@Override
	public Message sendMessageAndPresent(MessagePostReq message) {
		Optional<Present> oPresent = presentRepository.findById(Long.parseLong(message.getPresent_id()));
		Present present = oPresent.get();
		present.setReceivedPrice(present.getReceivedPrice() + message.getPrice());
		presentRepository.save(present);

		Message newMessage = Message.builder()
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
		return messageRepository.sumPeekMoney(userId);
	}
}
