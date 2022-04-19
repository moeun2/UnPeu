package com.unpue.service.iface;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.unpue.domain.entity.Present;
import com.unpue.domain.request.PresentPostReq;

public interface IPresentService {

	@Transactional
	Present createPresent(PresentPostReq present);

	@Transactional
	void deletePresent(Long presentId);

	@Transactional
	Present updatePresent(Long presentId,PresentPostReq present);

	@Transactional
	List<Present> getPresentListByUserId(Long userId);


}
