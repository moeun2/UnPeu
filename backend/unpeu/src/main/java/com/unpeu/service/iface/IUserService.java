package com.unpeu.service.iface;

import java.util.Map;

import com.unpeu.domain.entity.User;

public interface IUserService {
	User findUserByUserLogin(String userLogin);
	User findUserById(Long userId);
	boolean chkDplByUserLogin(String userLogin);
	User addUser(Map<String, String> userInfo,String socialDomain);
	String getKakaoAccessToken (String code);
	Map<String, String> getKakaoUserInfo(String token);
	

}
