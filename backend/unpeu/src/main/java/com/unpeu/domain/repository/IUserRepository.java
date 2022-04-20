package com.unpeu.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unpeu.domain.entity.User;

public interface IUserRepository extends JpaRepository<User, Long> {
}
