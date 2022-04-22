package com.unpeu.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String category;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    @Builder
    public Board(String category, String title, String content, LocalDateTime createAt) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
    }

    // === 연관관계 편의 메소드 ===
    public void setUser(User user) {
        if (this.user != null) { // 기존에 이미 유저가 존재한다면
            this.user.getReviews().remove(this); // 관계를 끊는다.
        }
        this.user = user;
        user.getReviews().add(this);
    }

    // 게시글을 삭제하면 달려있는 댓글 모두 삭제
//    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> commentList = new ArrayList<>();

    public void updateBoardInfo(String category, String title, String content) {
        this.category = category;
        this.title = title;
        this.content = content;
    }

}
