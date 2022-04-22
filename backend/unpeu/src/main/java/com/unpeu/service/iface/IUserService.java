package com.unpeu.service.iface;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.unpeu.domain.entity.User;

public interface IUserService {
	User getUserByUserLogin(String userLogin);
	
	User getUserById(Long userId);
	
	boolean chkDplUser(String userLogin);
	
	@Transactional(rollbackFor = Exception.class)
	User addUser(Map<String, String> userInfo,String socialDomain);
	
	@Transactional(rollbackFor = Exception.class)
	User updateUserTitle(Long userId,String userTitle);
	
	@Transactional(rollbackFor = Exception.class)
	User updateUserInfo(Long userId,String userInfo);
	
	@Transactional(rollbackFor = Exception.class)
	User updateUserImg(Long userId,String userImg);
	
	String getKakaoAccessToken (String code);
	
	Map<String, String> getKakaoUserInfo(String token);
	
}
