package com.unpeu.api.diary;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unpeu.config.auth.JwtTokenUtil;
import com.unpeu.domain.entity.Board;
import com.unpeu.domain.entity.User;
import com.unpeu.domain.repository.IBoardRepository;
import com.unpeu.domain.repository.IUserRepository;
import com.unpeu.domain.request.BoardPostReq;
import com.unpeu.domain.response.BoardGetRes;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.persistence.EntityManager;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager em;

    @Autowired
    private IBoardRepository boardRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static User user;

    private final String USERLOGIN = "test@naver.com";

    private void clear() {
        em.flush();
        em.clear();
    }

    private String getAccessToken(){
        return JwtTokenUtil.getToken(USERLOGIN);
    }

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 깨짐 방지
                .apply(springSecurity()) // Spring Security를 Spring MVC 테스트와 통합할 때 필요한 모든 초기 세팅을 수행
                .alwaysDo(print())
                .build();

        user = userRepository.save(User.builder()
                .id(1L)
                .userLogin(USERLOGIN)
                .socialDomain("kakao")
                .userName("김싸피")
                .userTitle("오늘은 어른이날")
                .userInfo("선물 주는 사람 착한 사람")
                .userMusic("Music is My Life")
                .todayVisit(10L)
                .totalVisit(100L)
                .userImg("img")
                .createDate(LocalDateTime.now())
                .build());
        clear();
    }

    /**
     * userId에 맞는 category 목록 조회
     */
    @Test
    public void 카레고리_목록조회() throws Exception {
        //given
        Board createBoard1 = Board.builder()
                .user(user)
                .category("220505_어른이날")
                .title("어린이날? 어른이날!")
                .content("성인이 된 나에게 셀프선물")
                .build();
        Board saveBoard1 = boardRepository.save(createBoard1);

        Board createBoard2 = Board.builder()
                .user(user)
                .category("220606_생일")
                .title("Happy BirthDay")
                .content("생일 축하해!")
                .build();
        Board saveBoard2 = boardRepository.save(createBoard2);

        // when
        MvcResult result = mockMvc.perform(get("/api/board/category/" + user.getId())
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Map<String, Object> jsonObject = objectMapper.readValue(content, Map.class);
        String jsonString = objectMapper.writeValueAsString(jsonObject.get("category"));
        List<String> categories = objectMapper.readValue(jsonString, new TypeReference<List<String>>() {});

        // then
        Assertions.assertThat(categories).contains(saveBoard1.getCategory());
        Assertions.assertThat(categories).contains(saveBoard2.getCategory());
    }

    @Test
    public void 게시판_글_전체조회() throws Exception {
        // given
        Board createBoard1 = Board.builder()
                .user(user)
                .category("220505_어른이날")
                .title("어린이날? 어른이날!")
                .content("성인이 된 나에게 셀프선물")
                .build();
        Board saveBoard1 = boardRepository.save(createBoard1);

        Board createBoard2 = Board.builder()
                .user(user)
                .category("220505_어른이날")
                .title("하지만 가장 큰 선물은")
                .content("휴일이라는 것")
                .build();
        Board saveBoard2 = boardRepository.save(createBoard2);

        // when
        MvcResult result = mockMvc.perform(get("/api/board/" + user.getId() + "/" + createBoard1.getCategory())
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Map<String, Object> jsonObject = objectMapper.readValue(content, Map.class);
        String jsonString = objectMapper.writeValueAsString(jsonObject.get("boardList"));
        List<BoardGetRes> boardList = objectMapper.readValue(jsonString, new TypeReference<List<BoardGetRes>>() {});

        // then
        Assertions.assertThat(boardList.get(0).getBoardId()).isEqualTo(saveBoard1.getBoardId());
        Assertions.assertThat(boardList.get(1).getBoardId()).isEqualTo(saveBoard2.getBoardId());
    }

    @Test
    public void 게시판_글_상세조회() throws Exception {
        // given
        Board createBoard = Board.builder()
                .user(user)
                .category("220505_어른이날")
                .title("어린이날? 어른이날!")
                .content("성인이 된 나에게 셀프선물")
                .build();
        Board saveBoard = boardRepository.save(createBoard);

        // when
        MvcResult result = mockMvc.perform(get("/api/board/" + saveBoard.getBoardId())
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Map<String, Object> jsonObject = objectMapper.readValue(content, Map.class);
        String jsonString = objectMapper.writeValueAsString(jsonObject.get("boardInfo"));
        BoardGetRes boardInfo = objectMapper.readValue(jsonString, BoardGetRes.class);

        // then
        Assertions.assertThat(boardInfo.getCategory()).isEqualTo(createBoard.getCategory());
        Assertions.assertThat(boardInfo.getTitle()).isEqualTo(createBoard.getTitle());
        Assertions.assertThat(boardInfo.getContent()).isEqualTo(createBoard.getContent());
    }

    /**
     * 게시글 추가
     */
    @Test
    public void 게시판_글_추가_성공() throws Exception {
        // given
        BoardPostReq createBoard = BoardPostReq.builder()
                .userId(user.getId())
                .title("어린이날? 어른이날!")
                .content("성인이 된 나에게 셀프선물")
                .category("220505_어른이날")
                .build();
        String content = objectMapper.writeValueAsString(createBoard);

        // when
        mockMvc.perform(post("/api/board")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());

        // then
        Assertions.assertThat(boardRepository.findAll().size()).isEqualTo(1);
    }

    /**
     * user는 번호가 boardId인 게시글 수정
     */
    @Test
    public void 게시판_글_수정_성공() throws Exception {
        // given
        Board createBoard = Board.builder()
                .user(user)
                .category("220505_어른이날")
                .title("어린이날? 어른이날!")
                .content("성인이 된 나에게 셀프선물")
                .build();
        Board saveBoard = boardRepository.save(createBoard);

        BoardPostReq updateBoard = BoardPostReq.builder()
                .userId(user.getId())
                .title("Happy BirthDay")
                .content("생일 축하해!")
                .category("220606_생일")
                .build();
        String content = objectMapper.writeValueAsString(updateBoard);

        // when
        mockMvc.perform(put("/api/board/" + saveBoard.getBoardId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());

        // then
        Board findBoard = boardRepository.findById(saveBoard.getBoardId()).get();
        Assertions.assertThat(findBoard.getTitle()).isEqualTo(updateBoard.getTitle());
        Assertions.assertThat(findBoard.getContent()).isEqualTo(updateBoard.getContent());
        Assertions.assertThat(findBoard.getCategory()).isEqualTo(updateBoard.getCategory());
    }

    /**
     * user는 newUser가 작성한 번호가 boardId인 게시글 수정을 시도
     * 권한이 없으므로 실패
     */
    @Test
    public void 게시판_글_수정_권한없음_실패() throws Exception {
        // given
        User newUser = userRepository.save(User.builder().id(2L).userLogin("test@google.com").socialDomain("google").userName("test2").build());
        Board createBoard = Board.builder()
                .user(newUser)
                .category("220505_어른이날")
                .title("어린이날? 어른이날!")
                .content("성인이 된 나에게 셀프선물")
                .build();
        Board saveBoard = boardRepository.save(createBoard);

        BoardPostReq updateBoard = BoardPostReq.builder()
                .userId(newUser.getId())
                .title("Happy BirthDay")
                .content("생일 축하해!")
                .category("220606_생일")
                .build();
        String content = objectMapper.writeValueAsString(updateBoard);

        // when
        mockMvc.perform(put("/api/board/" + saveBoard.getBoardId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken()) // USER의 TOKEN값
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isForbidden());

        // then
        Board findBoard = boardRepository.findById(saveBoard.getBoardId()).get();
        Assertions.assertThat(findBoard.getTitle()).isEqualTo(createBoard.getTitle());
        Assertions.assertThat(findBoard.getContent()).isEqualTo(createBoard.getContent());
        Assertions.assertThat(findBoard.getCategory()).isEqualTo(createBoard.getCategory());
    }

    /**
     * user는 번호가 boardId인 게시글 삭제
     */
    @Test
    public void 게시판_글_삭제_성공() throws Exception {
        // given
        Board createBoard = Board.builder()
                .user(user)
                .category("220505_어른이날")
                .title("어린이날? 어른이날!")
                .content("성인이 된 나에게 셀프선물")
                .build();
        Board saveBoard = boardRepository.save(createBoard);

        // when
        mockMvc.perform(delete("/api/board/" + saveBoard.getBoardId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken()))
                .andExpect(status().isOk());

        // then
        Assertions.assertThat(boardRepository.findById(saveBoard.getBoardId())).isEmpty();
    }

    /**
     * user는 newUser가 작성한 번호가 boardId인 게시글 삭제를 시도
     * 권한이 없으므로 실패
     */
    @Test
    public void 게시판_글_삭제_권한없음_실패() throws Exception {
        // given
        User newUser = userRepository.save(User.builder().id(2L).userLogin("test@google.com").socialDomain("google").userName("test2").build());
        Board createBoard = Board.builder()
                .user(newUser)
                .category("220505_어른이날")
                .title("어린이날? 어른이날!")
                .content("성인이 된 나에게 셀프선물")
                .build();
        Board saveBoard = boardRepository.save(createBoard);

        // when
        mockMvc.perform(delete("/api/board/" + saveBoard.getBoardId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())) // USER의 TOKEN
                .andExpect(status().isForbidden());

        // then
        Assertions.assertThat(boardRepository.findById(saveBoard.getBoardId())).isPresent();
    }
}