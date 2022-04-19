package com.unpue.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unpue.domain.entity.Present;
import com.unpue.domain.entity.User;

public interface IUserRepository extends JpaRepository<User, Long> {
}
