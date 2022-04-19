package com.unpue.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unpue.domain.entity.Present;

public interface IPresentRepository extends JpaRepository<Present, Long> {
	@Query(value = "SELECT * FROM present where user_id = :userId", nativeQuery = true)
	List<Present> getPresentListByUserId(@Param("userId") Long userId);

}
