package com.unpeu.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.unpeu.domain.entity.Message;
import com.unpeu.domain.entity.Present;
import com.unpeu.domain.entity.User;
import com.unpeu.domain.repository.IMessageRepository;
import com.unpeu.domain.repository.IPresentRepository;
import com.unpeu.domain.repository.IUserRepository;
import com.unpeu.domain.request.MessagePostReq;
import com.unpeu.domain.request.PresentPostReq;
import com.unpeu.service.iface.IPresentService;

/**
 * IPresentServie를 implements 하여 구현합니다.
 */
@Service
public class PresentService implements IPresentService {
	private static final Logger logger = LoggerFactory.getLogger(PresentService.class);

	private IPresentRepository presentRepository;
	private IUserRepository userRepository;
	private IMessageRepository messageRepository;

	private static final String CATEGORY = "2022_어른이날";

	public PresentService(IPresentRepository presentRepository, IUserRepository userRepository,
		IMessageRepository messageRepository) {
		this.presentRepository = presentRepository;
		this.userRepository = userRepository;
		this.messageRepository = messageRepository;
	}

	/**
	 * 선물리스트에 선물 등록
	 * @param present
	 * @return
	 */
	@Override
	public Present createPresent(PresentPostReq present) {
		Optional<User> user = userRepository.findById(Long.parseLong(present.getUserId()));
		if (user.isEmpty()) {
			throw new NoSuchElementException("userId가 " + present.getUserId() + " 인 유저를 찾을 수 없습니다");
		}

		Present newPresent = Present.builder()
			.userId(user.get()) //Test시 null로 테스트 했음
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
		presentRepository.deleteById(presentId);
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
		Present prevPresent = oPresent.get();
		prevPresent.setPresentPrice(present.getPresentPrice());
		prevPresent.setPresentName(present.getPresentName());
		prevPresent.setPresentImg(present.getPresentImgUrl());
		prevPresent.setReceivedPrice(Integer.parseInt(present.getReceivedPrice()));
		presentRepository.save(prevPresent);
		return prevPresent;
	}

	/**
	 * userId에 따라 선물 리스트 전체 조회
	 * @param userId
	 * @return
	 */
	@Override
	public List<Present> getPresentListByUserId(Long userId) {
		return presentRepository.getPresentListByUserId(userId);
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
			.created_at(LocalDate.now())
			.present(present)
			.build();
		return messageRepository.save(newMessage);
	}
}
