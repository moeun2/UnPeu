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

    @Query(value = "SELECT category FROM board WHERE user_id = :userId GROUP BY category DESC", nativeQuery = true)
    List<String> findCategoryByUserId(@Param("userId") Long userId);
    List<Board> findByUserIdAndCategoryOrderByCreatedAtDesc(Long userId, String category);
    void deleteAllByUserId(Long userId);
}
