package com.unpeu.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unpeu.domain.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
	Optional<User> findUserByUserLogin(String userLogin);
	
	@Modifying
	@Query("update User u set u.todayVisit = 0")
	int updateTodayVisit();

	@Modifying
	@Query("update User u set u.todayVisit = u.todayVisit+1 , u.totalVisit=u.totalVisit+1 where u.id=:id")
	int increseVisit(@Param("id") Long userId);
	
}
