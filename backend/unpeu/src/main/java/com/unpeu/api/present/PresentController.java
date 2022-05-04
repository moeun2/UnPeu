package com.unpeu.api.present;

import static com.unpeu.config.exception.ErrorCode.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.unpeu.config.auth.UnpeuUserDetails;
import com.unpeu.config.exception.CustomException;
import com.unpeu.config.media.MediaService;
import com.unpeu.domain.entity.Message;
import com.unpeu.domain.entity.Present;
import com.unpeu.domain.entity.User;
import com.unpeu.domain.request.MessagePostReq;
import com.unpeu.domain.request.PresentPostReq;
import com.unpeu.service.iface.IPresentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

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

	@ApiOperation(value = "선물 등록 Controller")
	@RequestMapping(value = "/present", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Map<String, Object>> createPresent(@ApiIgnore @NotNull Authentication authentication,
		@Valid @ModelAttribute @NotNull PresentPostReq present) {
		logger.info("createPresent - 호출");
		if(present.getPresentImg() == null || present.getPresentName()==null || present.getPresentPrice() == null){
			throw new CustomException(PRESENT_NOT_FOUND);
		}
		// userId 설정 -> 현재 userId: 1로만 테스트 중
		UnpeuUserDetails userDetails = (UnpeuUserDetails)authentication.getDetails();
		User user = userDetails.getUser();
		present.setUserId(String.valueOf(user.getId()));
		// 이미지 -> url로 변경 설정
		String url = mediaService.save(present.getPresentImg());
		logger.info("media Saved Url : " + url);
		present.setPresentImgUrl(url);
		Map<String, Object> resultMap = new HashMap<>();
		Present newPresent = this.presentService.createPresent(present);
		resultMap.put("Present", newPresent);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "선물 수정 Controller")
	@RequestMapping(value = "/present/{presentId}", method = RequestMethod.PUT, consumes = {
		MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Map<String, Object>> updatePresent(@NotNull @PathVariable("presentId") Long presentId,
		@Valid @ModelAttribute @NotNull PresentPostReq present) {
		logger.info("updatePresent - 호출");
		if (present.getPresentImg() != null) {
			String url = mediaService.save(present.getPresentImg());
			present.setPresentImgUrl(url);
		}
		Map<String, Object> resultMap = new HashMap<>();
		Present newPresent = this.presentService.updatePresent(presentId, present);
		resultMap.put("Present", newPresent);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "선물 삭제 Controller")
	@RequestMapping(value = "/present/{presentId}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> deletePresent(@NotNull @PathVariable("presentId") Long presentId) {
		logger.info("deletePresent - 호출");
		this.presentService.deletePresent(presentId);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("Message", "Delete Present Success");
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "선물 리스트 조회 Controller")
	@RequestMapping(value = "/present/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getPresent(@NotNull @PathVariable("userId") Long userId) {
		logger.info("getPresent - 호출");
		List<Present> presentList = presentService.getPresentListByUserId(userId);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("Present", presentList);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "선물 & 메세지 보내기 Controller")
	@RequestMapping(value = "/present/message", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> sendMessageAndPresent(@Valid @RequestBody MessagePostReq message) {
		logger.info("sendMessageAndPresent - 호출");
		Map<String, Object> resultMap = new HashMap<>();
		Message newMessage = presentService.sendMessageAndPresent(message);
		resultMap.put("Message", newMessage);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "엿보기 Controller")
	@RequestMapping(value = "/present/meesage/money", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> peekMoney() {
		logger.info("peekMoney - 호출");
		User user = new User();
		String money = presentService.peekMoney(user.getId());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("Money", money);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
	}

}
