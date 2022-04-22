package com.unpeu.api.users;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<Map<String, Object>> kakaoLoginAndSignup(@RequestParam String code){
		logger.info("kakaoLoginAndSignup - 호출");
        String token=userService.getKakaoAccessToken(code);
        Map<String, String> userInfo=userService.getKakaoUserInfo(token);
        Map<String, Object> resultMap = new HashMap<>();
        
        if(!userService.chkDplByUserLogin(userInfo.get("userLogin"))) {
        	logger.info("kakaoLoginAndSignup - 회원가입 진행");
            User signUser = userService.addUser(userInfo,"kakao");
        }
		resultMap.put("accessToken",JwtTokenUtil.getToken(userInfo.get("userLogin")));
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
    }
	
	
	
	@ApiOperation(value = "유저 정보 조회(회원) Controller")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getUserInfoByToken(@ApiIgnore Authentication authentication){
		logger.info("getUserInfoByToken - 호출");
		Map<String, Object> resultMap = new HashMap<>();
        UnpeuUserDetails userDetails = (UnpeuUserDetails) authentication.getDetails();
        User user =userDetails.getUser();
        
		resultMap.put("User",user);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
    }
	
	
	
	@ApiOperation(value = "유저 정보 조회(비회원) Controller")
	@RequestMapping(value = "/users/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getUserInfoByUserId(@PathVariable("user_id") Long userId){
		logger.info("getUserInfoByUserId - 호출");
		Map<String, Object> resultMap = new HashMap<>();
        User user =userService.findUserById(userId);
        
		resultMap.put("User",user);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
    }
	
	
	
	@ApiOperation(value = "유저 타이틀 수정 Controller")
	@RequestMapping(value = "/users/title", method = RequestMethod.PATCH)
    public ResponseEntity<Map<String, Object>> updateUserTitle(@ApiIgnore Authentication authentication,@RequestParam String userTitle){
		logger.info("updateUserTitle - 호출");
		Map<String, Object> resultMap = new HashMap<>();
        UnpeuUserDetails userDetails = (UnpeuUserDetails) authentication.getDetails();
        User user =userDetails.getUser();
        
        User updateUser=userService.updateUserTitle(user.getId(), userTitle);
        
		resultMap.put("User",updateUser);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
    }
	
	
	
	@ApiOperation(value = "유저 info 수정 Controller")
	@RequestMapping(value = "/users/info", method = RequestMethod.PATCH)
    public ResponseEntity<Map<String, Object>> updateUserInfo(@ApiIgnore Authentication authentication,@RequestParam String userInfo){
		logger.info("updateUserInfo - 호출");
		Map<String, Object> resultMap = new HashMap<>();
        UnpeuUserDetails userDetails = (UnpeuUserDetails) authentication.getDetails();
        User user =userDetails.getUser();
        
        User updateUser=userService.updateUserInfo(user.getId(), userInfo);
        
		resultMap.put("User",updateUser);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
    }
	
	
	
	@ApiOperation(value = "유저 이미지 수정 Controller")
	@RequestMapping(value = "/users/img", method = RequestMethod.PATCH)
    public ResponseEntity<Map<String, Object>> updateUserImg(@ApiIgnore Authentication authentication,
    		@RequestPart(value = "file") final MultipartFile userImg){
		logger.info("updateUserImg - 호출");
		Map<String, Object> resultMap = new HashMap<>();
        UnpeuUserDetails userDetails = (UnpeuUserDetails) authentication.getDetails();
        User user =userDetails.getUser();
        
        if (userImg != null) {
        	logger.info("updateUserImg - 이미지 없음");
			String url = mediaService.save(userImg);
			user=userService.updateUserImg(user.getId(), url);
		}
		resultMap.put("User",user);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
    }
	
	
}
