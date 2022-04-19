package com.unpue.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.unpue.config.media.GlobalConfig;
import com.unpue.domain.entity.Present;
import com.unpue.domain.entity.User;
import com.unpue.domain.repository.IPresentRepository;
import com.unpue.domain.request.PresentPostReq;
import com.unpue.service.iface.IPresentService;

/**
 * IPresentServie를 implements 하여 구현합니다.
 */
@Service
public class PresentService implements IPresentService {
	private static final Logger logger = LoggerFactory.getLogger(PresentService.class);

	private IPresentRepository presentRepository;

	public PresentService(IPresentRepository presentRepository) {
		this.presentRepository = presentRepository;
	}

	/**
	 * 선물리스트에 선물 등록
	 * @param present
	 * @return
	 */
	@Override
	public Present createPresent(PresentPostReq present) {

		// User 완성되면 user 객체 넣기 or accessToken에서 가져오기
		Present newPresent = Present.builder()
			.user(null)
			.presentImg(present.getPresentImgUrl())
			.presentName(present.getPresentName())
			.presentPrice(present.getPresentPrice())
			.receivedPrice(0)
			.build();

		presentRepository.save(newPresent);
		return newPresent;
	}
}
