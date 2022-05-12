package com.unpeu.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
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
    
    private LocalDate userBirth;
    private String userEmail;
    
    private Long todayVisit;
    private Long totalVisit;

	private String userImg;

	@NotNull(message = "category 를 입력해주세요")
	private String category;

	@NotNull(message = "selectedDate 를 입력해주세요")
	private LocalDate selectedDate;

	private LocalDateTime createdAt;
}




