package com.unpeu.api.users;

import java.util.HashMap;
import java.util.Map;


import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.unpeu.api.present.PresentController;
import com.unpeu.config.auth.JwtTokenUtil;
import com.unpeu.config.auth.UnpeuUserDetails;
import com.unpeu.config.media.MediaService;
import com.unpeu.domain.entity.User;
import com.unpeu.domain.request.UserPatchEmailBirthReq;
import com.unpeu.domain.request.UserPatchUserInfoReq;
import com.unpeu.domain.response.UserGetRes;
import com.unpeu.service.iface.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Api("User 관련 기능")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private final IUserService userService;
	private final MediaService mediaService;

	@ApiOperation(value = "카카오 로그인/회원가입  Controller")
	@RequestMapping(value = "/auth/kakao", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> kakaoLoginAndSignup(@RequestParam @NotNull String code){
		logger.info("kakaoLoginAndSignup - 호출");
		Map<String, Object> resultMap = new HashMap<>();
        String token=userService.getKakaoAccessToken(code);
        if(token==null) {
        	resultMap.put("message","token doesn't exist");
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
        
        Map<String, String> userInfo=userService.getKakaoUserInfo(token);
        if(userInfo==null) {
        	resultMap.put("message","userInfo doesn't exist");
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
        
        if(!userService.chkDplUser(userInfo.get("userLogin"))) {
        	logger.info("kakaoLoginAndSignup - 회원가입 진행");
            User signUser = userService.addUser(userInfo,"kakao");
        }
        
		resultMap.put("accessToken",JwtTokenUtil.getToken(userInfo.get("userLogin")));
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
    }
	
	@ApiOperation(value = "구글 로그인/회원가입  Controller")
	@RequestMapping(value = "/auth/google", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> googleLoginAndSignup(@RequestParam @NotNull String code){
		logger.info("googleLoginAndSignup - 호출");
		Map<String, Object> resultMap = new HashMap<>();
        String token=userService.getGoogleAccessToken(code);
        if(token==null) {
        	resultMap.put("message","token doesn't exist");
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
        
        Map<String, String> userInfo=userService.getGoogleUserInfo(token);
        
        if(userInfo==null) {
        	resultMap.put("message","userInfo doesn't exist");
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
        
        if(!userService.chkDplUser(userInfo.get("userLogin"))) {
        	logger.info("googleLoginAndSignup - 회원가입 진행");
            User signUser = userService.addUser(userInfo,"google");
        }       
		resultMap.put("accessToken",JwtTokenUtil.getToken(userInfo.get("userLogin")));
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
    }

	
	@ApiOperation(value = "유저 정보 조회(회원) Controller")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getUserInfoByToken(@ApiIgnore @NotNull Authentication authentication){
		logger.info("getUserInfoByToken - 호출");
		Map<String, Object> resultMap = new HashMap<>();
		UnpeuUserDetails userDetails = (UnpeuUserDetails)authentication.getDetails();
		User user = userDetails.getUser();

		resultMap.put("User", new UserGetRes(user));
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "유저 정보 조회(비회원) Controller")
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getUserInfoByUserId(@PathVariable("userId") @NotNull Long userId){
		logger.info("getUserInfoByUserId - 호출");
		Map<String, Object> resultMap = new HashMap<>();
		User user=null;
		try {
			user =userService.getUserById(userId);
		}catch (Exception e) {
			logger.info("getUserInfoByUserId - 없는 유저입니다. ");
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
		}
        
		resultMap.put("User",new UserGetRes(user));
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
    }
	
	
	
	@ApiOperation(value = "유저 타이틀 수정 Controller")
	@RequestMapping(value = "/users/title", method = RequestMethod.PATCH)
    public ResponseEntity<Map<String, Object>> updateUserTitle(@ApiIgnore @NotNull Authentication authentication,@RequestParam @NotNull String userTitle){
		logger.info("updateUserTitle - 호출");
		Map<String, Object> resultMap = new HashMap<>();
		UnpeuUserDetails userDetails = (UnpeuUserDetails)authentication.getDetails();
		User user = userDetails.getUser();
		
		User updateUser = userService.updateUserTitle(user.getId(), userTitle);

		resultMap.put("User", new UserGetRes(updateUser));
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "유저 info 수정 Controller")
	@RequestMapping(value = "/users/info", method = RequestMethod.PATCH)
    public ResponseEntity<Map<String, Object>> updateUserInfo(@ApiIgnore @NotNull Authentication authentication,@RequestBody @NotNull UserPatchUserInfoReq userPatchReq){
		logger.info("updateUserInfo - 호출");
		Map<String, Object> resultMap = new HashMap<>();
		UnpeuUserDetails userDetails = (UnpeuUserDetails)authentication.getDetails();
		User user = userDetails.getUser();

		User updateUser = userService.updateUserInfo(user.getId(), userPatchReq.getUserInfo());

		resultMap.put("User",  new UserGetRes(updateUser));
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
	}
	
	@ApiOperation(value = "유저 email, Birth 수정 Controller")
	@RequestMapping(value = "/users/email/birth", method = RequestMethod.PATCH)
    public ResponseEntity<Map<String, Object>> updateUserEmailBirth(@ApiIgnore @NotNull Authentication authentication,@RequestBody @NotNull UserPatchEmailBirthReq userPatchEmailBirthReq){
		logger.info("updateUserEmailBirth - 호출");
		Map<String, Object> resultMap = new HashMap<>();
		UnpeuUserDetails userDetails = (UnpeuUserDetails)authentication.getDetails();
		User user = userDetails.getUser();

		User updateUser = userService.updateUserEmailBirth(user.getId(), userPatchEmailBirthReq);
		
		resultMap.put("User", new UserGetRes(updateUser));
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "유저 이미지 수정 Controller")
	@RequestMapping(value = "/users/img", method = RequestMethod.PATCH)
    public ResponseEntity<Map<String, Object>> updateUserImg(@ApiIgnore @NotNull Authentication authentication,
    		@RequestPart(value = "file") @NotNull MultipartFile userImg){
		logger.info("updateUserImg - 호출");
		Map<String, Object> resultMap = new HashMap<>();
		UnpeuUserDetails userDetails = (UnpeuUserDetails)authentication.getDetails();
		User user = userDetails.getUser();

		if (userImg != null) {
			logger.info("updateUserImg - 이미지 없음");
			String url = mediaService.save(userImg);
			user = userService.updateUserImg(user.getId(), url);
		}
		resultMap.put("User", new UserGetRes(user));
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
	}
	
	@ApiOperation(value = "조회수 증가 Controller")
	@RequestMapping(value = "/users/visit/{userId}", method = RequestMethod.PATCH)
    public ResponseEntity<Map<String, Object>> updateVisit(@PathVariable @NotNull Long userId){
		logger.info("updateVisit - 호출");
		Map<String, Object> resultMap = new HashMap<>();
		User user=userService.increseVisit(userId);
		resultMap.put("User",  new UserGetRes(user));
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
	}
	
	@ApiOperation(value = "회원탈퇴 Controller")
	@RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, Object>> deleteUser(@ApiIgnore @NotNull Authentication authentication){
		logger.info("deleteUser - 호출");
		Map<String, Object> resultMap = new HashMap<>();
		UnpeuUserDetails userDetails = (UnpeuUserDetails)authentication.getDetails();
		User user = userDetails.getUser();
		
		userService.deleteUser(user.getId());
		resultMap.put("message", "success");
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
		
	}
	

}
