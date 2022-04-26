package com.unpeu.domain.repository;

import com.unpeu.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ICommentRepository extends JpaRepository<Comment, Long> {
}
