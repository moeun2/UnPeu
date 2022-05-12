package com.unpeu.service.impl;

import static com.unpeu.config.exception.ErrorCode.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unpeu.config.exception.CustomException;
import com.unpeu.domain.entity.Present;
import com.unpeu.domain.entity.User;
import com.unpeu.domain.repository.IBoardRepository;
import com.unpeu.domain.repository.IMessageRepository;
import com.unpeu.domain.repository.IPresentRepository;
import com.unpeu.domain.repository.IUserRepository;
import com.unpeu.domain.request.UserPatchConceptReq;
import com.unpeu.domain.request.UserPatchEmailBirthReq;
import com.unpeu.domain.request.UserPatchUserInfoReq;
import com.unpeu.service.iface.IUserService;

import lombok.RequiredArgsConstructor;

@Service("userService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService{
	
	private final IUserRepository userRepository;
	private final IMessageRepository messageRepository;
	private final IPresentRepository presentRepository;
	private final IBoardRepository boardRepository;
	
	@Value("${spring.server.url}")
	private String RedirectURL;

	/**
	 * userLogin정보로 사용자 조회
	 * @param userLogin
	 * @return
	 */
	@Override
	public User getUserByUserLogin(String userLogin) {
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
            sb.append("&redirect_uri="+RedirectURL+"/login/kakao");
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
	
	@Override
	public String getGoogleAccessToken (String code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://oauth2.googleapis.com/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=530350751299-fbiks9onutpnvmgebr0fc5uvllj5fidn.apps.googleusercontent.com");//kakao restapi키
            sb.append("&client_secret=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");
			sb.append("&redirect_uri="+RedirectURL+"/login/google");
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
	 * 토큰으로 구글 유저정보 얻어오기
	 * @param token
	 * @return
	 */
	@Override
	public Map<String, String> getGoogleUserInfo(String token) {
		System.out.println(token);
		String reqURL = "https://www.googleapis.com/oauth2/v2/userinfo";
		
		Map<String, String> userInfo = new HashMap<>();

	    try {
	       URL url = new URL(reqURL);
	       HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	       conn.setRequestMethod("GET");
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

	       try 
           {
           	Map<String, String> map = mapper.readValue(result, Map.class);
           	nickname=(String)map.get("name");
           	id=(String)map.get("id");
           } catch (IOException e) {
           	e.printStackTrace(); 
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
	public boolean chkDplUser(String userLogin) {
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
	@Transactional(rollbackFor = Exception.class)
	@Override
	public User addUser(Map<String, String> userInfo, String socialDomain) {
		User user = new User();
		user.setUserLogin(userInfo.get("userLogin"));
		user.setUserName(userInfo.get("name"));
		user.setSocialDomain(socialDomain);
		user.setTodayVisit(0L);
		user.setTotalVisit(0L);
		user.setIsAgree(true);

		user.setCreatedAt(LocalDateTime.now());
		return userRepository.save(user);
	}

	@Override
	public User updateUserConcept(Long userId, UserPatchConceptReq userPatchConceptReq) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isEmpty()) {
			throw new CustomException(MEMBER_NOT_FOUND);
		}
		User newUser= user.get();

		String category = userPatchConceptReq.getCategory();
		LocalDate selectedDate = userPatchConceptReq.getSelectedDate();
		if( category == null || selectedDate == null){
			throw new CustomException(CONCEPT_NOT_FOUND);
		}

		newUser.setCategory(category);
		newUser.setSelectedDate(selectedDate);
		return userRepository.save(newUser);
	}

	/**
	 * 해당하는 id의 유저 찾기
	 * @param userId
	 * @return
	 */
	@Override
	public User getUserById(Long userId) {
		Optional<User> user= userRepository.findById(userId);
		if (user.isEmpty()) {
			throw new CustomException(MEMBER_NOT_FOUND);
		}
		return user.get();
	}

	/**
	 * 유저의 title수정 
	 * @param userId
	 * @param userTitle
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
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
	@Transactional(rollbackFor = Exception.class)
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
	@Transactional(rollbackFor = Exception.class)
	@Override
	public User updateUserImg(Long userId,String userImg) {
		User user= userRepository.findById(userId).get();
		user.setUserImg(userImg);
		return userRepository.save(user);
	}
	
	/**
	 * 유저의 이메일 생년월일 수정
	 * @param userPatchEmailBirthReq
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public User updateUserEmailBirth(Long userId,UserPatchEmailBirthReq userPatchEmailBirthReq) {
		User user= userRepository.findById(userId).get();
		user.setUserBirth(userPatchEmailBirthReq.getUserBirth());
		user.setUserEmail(userPatchEmailBirthReq.getUserEmail());
		user.setIsAgree(userPatchEmailBirthReq.getIsAgree());
		return userRepository.save(user);
	}


	/**
	 * 일일 방문자수 초기회
	 * @return
	 */
	@Override
	@Transactional(readOnly = false)
	public void updateTodayVisit() {
		userRepository.updateTodayVisit();
	}

	/**
	 * 일일 방문자수 증가
	 * @param userId
	 * @return
	 */
	@Override
	@Transactional(readOnly = false)
	public User increseVisit(Long userId) {
		userRepository.increseVisit(userId);
		return userRepository.findById(userId).get();
	}

	/**
	 * 회원 삭제
	 * @param userId
	 * @return
	 */
	@Override
	@Transactional(readOnly = false)
	public void deleteUser(Long userId) {
		try {
			presentRepository.deleteAllByUserId(userId);
			messageRepository.deleteAllByUserId(userId);
			boardRepository.deleteAllByUserId(userId);
			userRepository.deleteById(userId);
		} catch (Exception e) {
			throw new CustomException(DELETE_CONFLICT);
		}
	}

}
