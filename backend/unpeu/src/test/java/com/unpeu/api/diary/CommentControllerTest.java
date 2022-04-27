package com.unpeu.api.diary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unpeu.config.auth.JwtTokenUtil;
import com.unpeu.domain.entity.Board;
import com.unpeu.domain.entity.Comment;
import com.unpeu.domain.entity.User;
import com.unpeu.domain.repository.IBoardRepository;
import com.unpeu.domain.repository.ICommentRepository;
import com.unpeu.domain.repository.IUserRepository;
import com.unpeu.domain.request.CommentPostReq;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IBoardRepository boardRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICommentRepository commentRepository;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 깨짐 방지
                .apply(springSecurity()) // Spring Security를 Spring MVC 테스트와 통합할 때 필요한 모든 초기 세팅을 수행
                .alwaysDo(print())
                .build();
    }

    public User saveUser() {
        return userRepository.save(User.builder()
                .id(1L)
                .userLogin("test@naver.com")
                .socialDomain("kakao").userName("김싸피")
                .build());
    }

    public Board saveBoard() {
        User user = saveUser();
        Board save = boardRepository.save(Board.builder()
                .category("220505_어른이날")
                .title("어린이날? 어른이날!")
                .content("성인이 된 나에게 셀프선물")
                .user(user)
                .build());

        return save;
    }

    public Comment saveComment() {
        Board board = saveBoard();
        Comment comment = Comment.builder()
                .writer("작성자")
                .password("1234")
                .content("생일축하해")
                .build();
        comment.setBoard(board);

        return commentRepository.save(comment);
    }

    /**
     * 댓글 등록
     */
    @Test
    public void 댓글_등록_성공() throws Exception {
        // given
        Long boardId = saveBoard().getBoardId();
        CommentPostReq createComment = new CommentPostReq("작성자", "1234", "댓글내용작성");
        String content = objectMapper.writeValueAsString(createComment);

        // when
        mockMvc.perform(post("/api/comment/" + boardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());

        // then
        Optional<Board> oBoard = boardRepository.findById(boardId);
        Assertions.assertThat(oBoard.get().getComments().size()).isEqualTo(1);
    }

    /**
     * 댓글을 등록할 게시글이 없는 경우
     */
    @Test
    public void 댓글_등록_게시글없음_실패() throws Exception {
        // given
        Long boardId = 0L; // 존재할 수 없는 번호
        CommentPostReq createComment = new CommentPostReq("작성자", "1234", "댓글내용작성");
        String content = objectMapper.writeValueAsString(createComment);

        // when
        mockMvc.perform(post("/api/comment/" + boardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isNotFound());
    }

    /**
     * 댓글 수정
     */
    @Test
    public void 댓글_수정_성공() throws Exception {
        // given
        Long commentId = saveComment().getCommentId();
        CommentPostReq updateComment = new CommentPostReq("작성자", "1234", "댓글내용작성");
        String content = objectMapper.writeValueAsString(updateComment);

        // when
        mockMvc.perform(put("/api/comment/"+commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());

        // then
        Optional<Comment> oComment = commentRepository.findById(commentId);
        Assertions.assertThat(oComment.get().getContent()).isEqualTo(updateComment.getContent());
    }

    /**
     * 댓글의 다른 비밀번호로 댓글 수정 시도
     * 권한없음으로 실패
     */
    @Test
    public void 댓글_수정_권한없음_실패() throws Exception {
        // given
        Long commentId = saveComment().getCommentId();
        CommentPostReq updateComment = new CommentPostReq("작성자", "5678", "댓글내용작성");
        String content = objectMapper.writeValueAsString(updateComment);

        // when
        mockMvc.perform(put("/api/comment/"+commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isForbidden());

        // then
        Optional<Comment> oComment = commentRepository.findById(commentId);
        Assertions.assertThat(oComment.get().getContent()).isEqualTo(saveComment().getContent());
    }

    /**
     * 댓글 삭제
     */
    @Test
    public void 댓글_실패_성공() throws Exception {
        // given
        Long commentId = saveComment().getCommentId();
        String password = "1234";

        // when
        mockMvc.perform(delete("/api/comment/" + commentId + "/" + password))
                .andExpect(status().isOk());

        // then
        Assertions.assertThat(commentRepository.findById(commentId)).isEmpty();
    }

    /**
     * 댓글의 다른 비밀번호로 댓글 삭제 시도
     * 권한없음으로 실패
     */
    @Test
    public void 댓글_실패_권한없음_실패() throws Exception {
        // given
        Long commentId = saveComment().getCommentId();
        String password = "5678";

        // when
        mockMvc.perform(delete("/api/comment/" + commentId + "/" + password))
                .andExpect(status().isForbidden());

        // then
        Assertions.assertThat(commentRepository.findById(commentId)).isPresent();
    }
}