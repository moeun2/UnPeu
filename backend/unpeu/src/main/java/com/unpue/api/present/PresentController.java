package com.unpue.api.present;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.unpue.config.media.MediaService;
import com.unpue.domain.entity.Present;
import com.unpue.domain.request.PresentPostReq;
import com.unpue.service.iface.IPresentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Present 관련 기능")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PresentController {
	public static final Logger logger = LoggerFactory.getLogger(PresentController.class);

	private IPresentService presentService;
	private MediaService mediaService;

	@Autowired
	public PresentController(IPresentService presentService, MediaService mediaService) {
		Assert.notNull(presentService, "presentService 객체가 반드시 필요!");
		this.presentService = presentService;
		this.mediaService = mediaService;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public Present test(@RequestParam MultipartFile file) {
		logger.info("test 호출");
		logger.info(file.getOriginalFilename());
		return null;
	}

	@ApiOperation(value = "선물 등록 Controller")
	@RequestMapping(value = "/present", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Present createPresent(@Valid @ModelAttribute PresentPostReq present) {
		logger.info("createPresent - 호출");
		String url = mediaService.save(present.getPresentImg());
		logger.info("media Saved Url : " + url);
		present.setPresentImgUrl(url);
		Present newPresent = this.presentService.createPresent(present);
		return newPresent;
	}

}
