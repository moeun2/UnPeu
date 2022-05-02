package com.unpeu.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.Hibernate;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "message")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_id")
	private Long messageId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "present_id")
	@ToString.Exclude
	private Present present;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	@ToString.Exclude
	private User user;

	
	@NotNull(message = "sender를 입력해주세요")
	private String sender;

	@Column(length=500)
	private String content;

	private String category;
	private int price;
	private LocalDateTime createdAt = LocalDateTime.now();

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		Message message = (Message)o;
		return messageId != null && Objects.equals(messageId, message.messageId);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
