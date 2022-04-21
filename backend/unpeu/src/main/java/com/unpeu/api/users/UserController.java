package com.unpeu.api.users;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unpeu.config.auth.JwtTokenUtil;
import com.unpeu.config.auth.UnpeuUserDetails;
import com.unpeu.domain.entity.User;
import com.unpeu.service.iface.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;


@Api("User 관련 기능")
@RestController
@RequestMapping("/api")
public class UserController {
	
	private final IUserService userService;

	@Autowired
	public UserController(IUserService userService,PasswordEncoder passwordEncoder) {
		this.userService = userService;
	}
	
	
	@ApiOperation(value = "카카오 로그인/회원가입  Controller")
	@RequestMapping(value = "/auth/kakao", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> kakaoLoginAndSignup(@RequestParam String code){
        String token=userService.getKakaoAccessToken(code);
        Map<String, String> userInfo=userService.getKakaoUserInfo(token);
        Map<String, Object> resultMap = new HashMap<>();
        
        if(!userService.chkDplByUserLogin(userInfo.get("userLogin"))) {
            User signUser = userService.addUser(userInfo,"kakao");
        }
		resultMap.put("accessToken",JwtTokenUtil.getToken(userInfo.get("userLogin")));
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
    }
	
	
	
	@ApiOperation(value = "유저 정보 조회(회원) Controller")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getUserInfoUByToken(@ApiIgnore Authentication authentication){
		Map<String, Object> resultMap = new HashMap<>();
        UnpeuUserDetails userDetails = (UnpeuUserDetails) authentication.getDetails();
        User user =userDetails.getUser();
        
		resultMap.put("User",user);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
    }
	
	
	
	@ApiOperation(value = "유저 정보 조회(비회원) Controller")
	@RequestMapping(value = "/users/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getUserInfoByUserId(@PathVariable("user_id") Long userId){
		System.out.println(userId);
		Map<String, Object> resultMap = new HashMap<>();
        User user =userService.findUserById(userId);
        
		resultMap.put("User",user);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
    }
	
}
