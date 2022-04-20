package com.unpeu.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unpeu.domain.entity.Message;
import com.unpeu.domain.entity.Present;

public interface IMessageRepository extends JpaRepository<Message, Long> {
}
