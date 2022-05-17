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

	@NotNull
	private String userLogin;
	@NotNull
	private String socialDomain;

	@NotNull
    private String userName;
	
    private String userTitle;
    private String userInfo;
    
    @NotNull
    private String userMusic;
    
    private LocalDate userBirth;
    private String userEmail;
    
    @NotNull
    private Long todayVisit;
    @NotNull
    private Long totalVisit;

	private String userImg;

	private String category;
	private LocalDate selectedDate;
	
	@NotNull
	private Boolean isAgree;

	@NotNull
	private LocalDateTime createdAt;
}




