package com.unpeu.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unpeu.domain.entity.Present;
import com.unpeu.domain.entity.User;
import com.unpeu.domain.repository.IUserRepository;
import com.unpeu.service.iface.IUserService;

import lombok.RequiredArgsConstructor;

@Service("userService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService{
	
	private final IUserRepository userRepository;

	/**
	 * userLogin정보로 사용자 조회
	 * @param userLogin
	 * @return
	 */
	@Override
	public User findUserByUserLogin(String userLogin) {
		User user = userRepository.findUserByUserLogin(userLogin).get();
		return user;
	}
	

	/**
	 * 인가코드로 엑세스토큰 가져오기
	 * @param code
	 * @return
	 */
	@Override
	public String getKakaoAccessToken (String code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=c0ad1801cdf80282754cf18e79556743");//kakao restapi키
            sb.append("&redirect_uri=http://localhost:8081/?Oauth=kakao");//redirect 경로
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);
            
            ObjectMapper mapper = new ObjectMapper(); 
            
            try 
            {
            	Map<String, String> map = mapper.readValue(result, Map.class);
            	access_Token=(String)map.get("access_token");
            	refresh_Token=(String)map.get("refresh_token");
            } catch (IOException e) {
            	e.printStackTrace(); 
            }

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 

        return access_Token;
    }
	
	/**
	 * 토큰으로 카카오 유저정보 얻어오기
	 * @param token
	 * @return
	 */
	@Override
	public Map<String, String> getKakaoUserInfo(String token) {
		System.out.println(token);
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		
		Map<String, String> userInfo = new HashMap<>();

	    try {
	       URL url = new URL(reqURL);
	       HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	       conn.setRequestMethod("POST");
	       conn.setDoOutput(true);
	       conn.setRequestProperty("Authorization", "Bearer " + token);

	       int responseCode = conn.getResponseCode();
	       System.out.println("responseCode : " + responseCode);

	       BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	       String line = "";
	       String result = "";

	       while ((line = br.readLine()) != null) {
	           result += line;
	       }
	       System.out.println("response body : " + result);
	       
	       ObjectMapper mapper = new ObjectMapper(); 
	       String nickname=null;
	       String id=null;

	       try {
	    	   Map<String, Object> map = mapper.readValue(result, Map.class);
	    	   Map<String,Object> kakao_account=(Map<String,Object>) map.get("kakao_account");
	    	   Map<String,Object> profile=(Map<String,Object>) kakao_account.get("profile");
	    	   nickname=(String) profile.get("nickname");
	    	   id=Long.toString((long)map.get("id"));
	       } catch (IOException e) {
	    	   e.printStackTrace(); 
	    	   return null;
	       }
	       userInfo.put("name", nickname);
	       userInfo.put("userLogin",id);
	      
	       br.close();

	       } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	       }
	    return userInfo;
	 }

	/**
	 * 중복된 userLogin값을 가지는 유저가 있는지 확인
	 * @param userLogin
	 * @return
	 */
	@Override
	public boolean chkDplByUserLogin(String userLogin) {
		if(userRepository.findUserByUserLogin(userLogin).isPresent())
			return true;
		else return false;
	}

	/**
	 * 유저 회원가입 
	 * @param userInfo
	 * @param socialDomain
	 * @return
	 */
	@Override
	public User addUser(Map<String, String> userInfo, String socialDomain) {
		User user = new User();
		user.setUserLogin(userInfo.get("userLogin"));
		user.setUserName(userInfo.get("name"));
		user.setSocialDomain(socialDomain);
		user.setTodayVisit(0L);
		user.setTotalVisit(0L);
		user.setCreateDate(LocalDateTime.now());
		
		return userRepository.save(user);
	}

	/**
	 * 해당하는 id의 유저 찾기
	 * @param userId
	 * @return
	 */
	@Override
	public User findUserById(Long userId) {
		User user= userRepository.findById(userId).get();
		return userRepository.save(user);
	}

	/**
	 * 유저의 title수정 
	 * @param userId
	 * @param userTitle
	 * @return
	 */
	@Override
	public User updateUserTitle(Long userId,String userTitle) {
		User user= userRepository.findById(userId).get();
		user.setUserTitle(userTitle);
		return userRepository.save(user);
	}

	/**
	 * 유저의 info 수정
	 * @param userId
	 * @param userInfo
	 * @return
	 */
	@Override
	public User updateUserInfo(Long userId,String userInfo) {
		User user= userRepository.findById(userId).get();
		user.setUserInfo(userInfo);
		return userRepository.save(user);
	}

	
	/**
	 * 유저의 프로필 이미지 수정
	 * @param userId
	 * @param userImg
	 * @return
	 */
	@Override
	public User updateUserImg(Long userId,String userImg) {
		User user= userRepository.findById(userId).get();
		user.setUserImg(userImg);
		return userRepository.save(user);
	}

}
