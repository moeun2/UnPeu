package com.unpeu.domain.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="board", indexes = {@Index(name = "cate_index", columnList = "category")})
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Size(max = 40)
    @NotNull(message = "카테고리를 입력해주세요")
    private String category;

    @Size(max = 30)
    @NotNull(message = "제목을 입력해주세요")
    private String title;

    @Size(max = 10000)
    @NotNull(message = "내용을 입력해주세요")
    private String content;

    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();

    // 게시글을 삭제하면 달려있는 댓글 모두 삭제
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<Comment>();

    @Builder
    public Board(User user, String category, String title, String content) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.content = content;
    }

    // Board에 message 저장하는 Builder (메세지 등록 날짜 포함)
    @Builder(builderClassName = "saveMessage", builderMethodName = "saveMessage")
    public Board(User user, String category, String title, String content, LocalDateTime createdAt) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    // ==== 비즈니스 로직 ====
    public void updateBoardInfo(String category, String title, String content) {
        this.category = category;
        this.title = title;
        this.content = content;
    }

}
