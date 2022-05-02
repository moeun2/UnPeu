package com.unpeu.service.iface;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.unpeu.domain.entity.User;
import com.unpeu.domain.request.UserPatchEmailBirthReq;
import com.unpeu.domain.request.UserPatchUserInfoReq;

public interface IUserService {
	User getUserByUserLogin(String userLogin);
	
	User getUserById(Long userId);
	
	boolean chkDplUser(String userLogin);
	
	User addUser(Map<String, String> userInfo,String socialDomain);
	
	User updateUserTitle(Long userId,String userTitle);
	
	User updateUserInfo(Long userId,String userInfo);
	
	User updateUserImg(Long userId,String userImg);
	
	User updateUserEmailBirth(Long userId,UserPatchEmailBirthReq userPatchEmailBirthReq);
	
	String getKakaoAccessToken (String code);
	
	Map<String, String> getKakaoUserInfo(String token);
	
	String getGoogleAccessToken (String code);
	
	Map<String, String> getGoogleUserInfo(String token);
	
}
