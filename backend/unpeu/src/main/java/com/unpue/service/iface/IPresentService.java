package com.unpue.service.iface;

import org.springframework.transaction.annotation.Transactional;

import com.unpue.domain.entity.Present;
import com.unpue.domain.request.PresentPostReq;

public interface IPresentService {

	@Transactional
	Present createPresent(PresentPostReq present);
}
