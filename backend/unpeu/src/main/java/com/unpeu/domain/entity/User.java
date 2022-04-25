package com.unpeu.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
	
	private String userLogin;
	private String socialDomain;

    private String userName;
    private String userTitle;
    private String userInfo;
    private String userMusic;
    
    private Long todayVisit;
    private Long totalVisit;

    private String userImg;
    
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "user")
    private List<Board> reviews = new ArrayList<>();


}


