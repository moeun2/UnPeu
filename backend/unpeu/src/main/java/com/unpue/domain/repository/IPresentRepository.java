package com.unpue.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unpue.domain.entity.Present;

public interface IPresentRepository extends JpaRepository<Present, Long> {
}
