package com.unpeu.api.diary;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.unpeu.config.auth.JwtTokenUtil;
import com.unpeu.domain.entity.Board;
import com.unpeu.domain.entity.User;
import com.unpeu.domain.repository.IBoardRepository;
import com.unpeu.domain.repository.IUserRepository;
import com.unpeu.domain.request.BoardPostReq;
import com.unpeu.domain.response.BoardGetRes;
import com.unpeu.domain.response.BoardInfoGetRes;
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
        // LocalDateTime ????????? ?????? ??????
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // ?????? ?????? ??????
                .apply(springSecurity()) // Spring Security??? Spring MVC ???????????? ????????? ??? ????????? ?????? ?????? ????????? ??????
                .alwaysDo(print())
                .build();

        user = userRepository.save(User.builder()
                .id(1L)
                .userLogin(USERLOGIN)
                .socialDomain("kakao")
                .userName("?????????")
                .userTitle("????????? ????????????")
                .userInfo("?????? ?????? ?????? ?????? ??????")
                .todayVisit(10L)
                .totalVisit(100L)
                .build());
        clear();
    }

    /**
     * userId??? ?????? category ?????? ??????
     */
    @Test
    public void ????????????_????????????() throws Exception {
        //given
        Board createBoard1 = Board.builder()
                .user(user)
                .category("220505_????????????")
                .title("????????????? ????????????!")
                .content("????????? ??? ????????? ????????????")
                .build();
        Board saveBoard1 = boardRepository.save(createBoard1);

        Board createBoard2 = Board.builder()
                .user(user)
                .category("220606_??????")
                .title("Happy BirthDay")
                .content("?????? ?????????!")
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

    /**
     * userId??? ?????? category??? ????????? ?????? ??????
     */
    @Test
    public void ?????????_???_????????????() throws Exception {
        // given
        Board createBoard1 = Board.builder()
                .user(user)
                .category("220505_????????????")
                .title("????????????? ????????????!")
                .content("????????? ??? ????????? ????????????")
                .build();
        Board saveBoard1 = boardRepository.save(createBoard1);

        Board createBoard2 = Board.builder()
                .user(user)
                .category("220505_????????????")
                .title("????????? ?????? ??? ?????????")
                .content("??????????????? ???")
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

    /**
     * boardId??? ?????? ????????? ?????? ??????
     */
    @Test
    public void ?????????_???_????????????() throws Exception {
        // given
        Board createBoard = Board.builder()
                .user(user)
                .category("220505_????????????")
                .title("????????????? ????????????!")
                .content("????????? ??? ????????? ????????????")
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
        BoardInfoGetRes boardInfo = objectMapper.readValue(jsonString, BoardInfoGetRes.class);

        // then
        Assertions.assertThat(boardInfo.getCategory()).isEqualTo(createBoard.getCategory());
        Assertions.assertThat(boardInfo.getTitle()).isEqualTo(createBoard.getTitle());
        Assertions.assertThat(boardInfo.getContent()).isEqualTo(createBoard.getContent());
        Assertions.assertThat(boardInfo.getCommentList()).isEqualTo(createBoard.getComments());
    }

    /**
     * ????????? ??????
     */
    @Test
    public void ?????????_???_??????_??????() throws Exception {
        // given
        BoardPostReq createBoard = BoardPostReq.builder()
                .userId(user.getId())
                .title("????????????? ????????????!")
                .content("????????? ??? ????????? ????????????")
                .category("220505_????????????")
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
     * user??? ????????? boardId??? ????????? ??????
     */
    @Test
    public void ?????????_???_??????_??????() throws Exception {
        // given
        Board createBoard = Board.builder()
                .user(user)
                .category("220505_????????????")
                .title("????????????? ????????????!")
                .content("????????? ??? ????????? ????????????")
                .build();
        Board saveBoard = boardRepository.save(createBoard);

        BoardPostReq updateBoard = BoardPostReq.builder()
                .userId(user.getId())
                .title("Happy BirthDay")
                .content("?????? ?????????!")
                .category("220606_??????")
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
     * user??? newUser??? ????????? ????????? boardId??? ????????? ????????? ??????
     * ????????? ???????????? ??????
     */
    @Test
    public void ?????????_???_??????_????????????_??????() throws Exception {
        // given
        User newUser = userRepository.save(User.builder().id(2L).userLogin("test@google.com").socialDomain("google").userName("test2").build());
        Board createBoard = Board.builder()
                .user(newUser)
                .category("220505_????????????")
                .title("????????????? ????????????!")
                .content("????????? ??? ????????? ????????????")
                .build();
        Board saveBoard = boardRepository.save(createBoard);

        BoardPostReq updateBoard = BoardPostReq.builder()
                .userId(newUser.getId())
                .title("Happy BirthDay")
                .content("?????? ?????????!")
                .category("220606_??????")
                .build();
        String content = objectMapper.writeValueAsString(updateBoard);

        // when
        mockMvc.perform(put("/api/board/" + saveBoard.getBoardId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken()) // USER??? TOKEN???
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
     * user??? ????????? boardId??? ????????? ??????
     */
    @Test
    public void ?????????_???_??????_??????() throws Exception {
        // given
        Board createBoard = Board.builder()
                .user(user)
                .category("220505_????????????")
                .title("????????????? ????????????!")
                .content("????????? ??? ????????? ????????????")
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
     * user??? newUser??? ????????? ????????? boardId??? ????????? ????????? ??????
     * ????????? ???????????? ??????
     */
    @Test
    public void ?????????_???_??????_????????????_??????() throws Exception {
        // given
        User newUser = userRepository.save(User.builder().id(2L).userLogin("test@google.com").socialDomain("google").userName("test2").build());
        Board createBoard = Board.builder()
                .user(newUser)
                .category("220505_????????????")
                .title("????????????? ????????????!")
                .content("????????? ??? ????????? ????????????")
                .build();
        Board saveBoard = boardRepository.save(createBoard);

        // when
        mockMvc.perform(delete("/api/board/" + saveBoard.getBoardId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())) // USER??? TOKEN
                .andExpect(status().isForbidden());

        // then
        Assertions.assertThat(boardRepository.findById(saveBoard.getBoardId())).isPresent();
    }
}