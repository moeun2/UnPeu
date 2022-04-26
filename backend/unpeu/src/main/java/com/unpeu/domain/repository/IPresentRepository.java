package com.unpeu.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unpeu.domain.entity.Message;
import com.unpeu.domain.entity.Present;
import com.unpeu.domain.entity.User;
@Repository
@Transactional(readOnly = true)
public interface IPresentRepository extends JpaRepository<Present, Long> {
	// @Query(value = "SELECT * FROM present where user_id = :userId", nativeQuery = true)
	// List<Present> getPresentListByUserId(@Param("userId") Long userId);
	List<Present> findPresentByUserId(Long userId);
	void deleteAllByUserId(Long userId);
}
