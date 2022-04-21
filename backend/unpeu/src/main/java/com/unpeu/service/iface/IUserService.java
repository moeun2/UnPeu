package com.unpeu.service.iface;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.unpeu.domain.entity.User;

public interface IUserService {
	@Transactional
	User findUserByUserLogin(String userLogin);
	
	@Transactional
	User findUserById(Long userId);
	
	@Transactional
	boolean chkDplByUserLogin(String userLogin);
	
	@Transactional
	User addUser(Map<String, String> userInfo,String socialDomain);
	
	@Transactional
	User updateUserTitle(Long userId,String userTitle);
	
	@Transactional
	User updateUserInfo(Long userId,String userInfo);
	
	@Transactional
	User updateUserImg(Long userId,String userImg);
	
	String getKakaoAccessToken (String code);
	
	Map<String, String> getKakaoUserInfo(String token);
	

}
