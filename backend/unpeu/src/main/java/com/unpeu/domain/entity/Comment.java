package com.unpeu.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Size(min = 2)
    @NotNull(message = "작성자 닉네임을 작성해주세요")
    private String writer;

    @Size(min = 4)
    @NotNull(message = "비밀번호를 작성해주세요")
    private String password;

    @NotNull(message = "댓글 내용을 작성해주세요")
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Comment(String writer, String password, String content) {
        this.writer = writer;
        this.password = password;
        this.content = content;
    }

    // ==== 연관 관계 편의 메소드 ====
    public void setBoard(Board board) {
        // 기존 관계 제거
        if(this.board != null) {
            this.board.getComments().remove(this);
        }
        this.board = board; // comment -> board
        board.getComments().add(this); // board -> comment
    }

    // ==== 비즈니스 로직 ====
    public boolean matchPassword(String checkPassword){
        return checkPassword.matches(getPassword());
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
