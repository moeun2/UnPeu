package com.unpeu.domain.repository;

import com.unpeu.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface IBoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT DISTINCT category FROM board WHERE user_id = :userId", nativeQuery = true)
    List<String> findCategoryByUserId(@Param("userId") Long userId);
    List<Board> findByUserIdAndCategory(Long userId, String category);
}
