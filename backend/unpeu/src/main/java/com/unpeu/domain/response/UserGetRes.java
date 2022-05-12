package com.unpeu.domain.response;

import java.time.LocalDate;

import com.unpeu.domain.entity.User;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@ApiModel("BoardGetRes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserGetRes {
	private Long id;
	
	private String socialDomain;

    private String userName;
    private String userTitle;
    private String userInfo;
    private String userMusic;
    
    private LocalDate userBirth;
    private String userEmail;
    
    private Long todayVisit;
    private Long totalVisit;

	private String userImg;
	
	private Boolean isAgree;

	public UserGetRes(User user) {
		super();
		this.id = user.getId();
		this.socialDomain = user.getSocialDomain();
		this.userName = user.getUserName();
		this.userTitle = user.getUserTitle();
		this.userInfo = user.getUserInfo();
		this.userMusic = user.getUserMusic();
		this.userBirth = user.getUserBirth();
		this.userEmail = user.getUserEmail();
		this.todayVisit = user.getTodayVisit();
		this.totalVisit = user.getTotalVisit();
		this.userImg = user.getUserImg();
		this.isAgree= user.getIsAgree();
	}


}
