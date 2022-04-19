package com.unpue.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.unpue.config.media.GlobalConfig;
import com.unpue.domain.entity.Present;
import com.unpue.domain.entity.User;
import com.unpue.domain.repository.IPresentRepository;
import com.unpue.domain.repository.IUserRepository;
import com.unpue.domain.request.PresentPostReq;
import com.unpue.service.iface.IPresentService;

/**
 * IPresentServie를 implements 하여 구현합니다.
 */
@Service
public class PresentService implements IPresentService {
	private static final Logger logger = LoggerFactory.getLogger(PresentService.class);

	private IPresentRepository presentRepository;
	private IUserRepository userRepository;

	public PresentService(IPresentRepository presentRepository, IUserRepository userRepository) {
		this.presentRepository = presentRepository;
		this.userRepository = userRepository;
	}

	/**
	 * 선물리스트에 선물 등록
	 * @param present
	 * @return
	 */
	@Override
	public Present createPresent(PresentPostReq present) {
		// User 완성되면 user 객체 넣기 or accessToken에서 가져오기
		// User user = new userRepository.findById(present.getUserId());
		Optional<User> user = userRepository.findById(Long.parseLong(present.getUserId()));
		Present newPresent = Present.builder()
			.userId(user.get())
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
	 * userId에 따라 선물 리스트 전체 조회
	 * @param userId
	 * @return
	 */
	@Override
	public List<Present> getPresentListByUserId(Long userId) {
		return presentRepository.getPresentListByUserId(userId);
	}
}
