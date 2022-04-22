package com.unpeu.api.diary;

import com.unpeu.config.auth.UnpeuUserDetails;
import com.unpeu.domain.entity.User;
import com.unpeu.domain.request.BoardPostReq;
import com.unpeu.service.iface.IBoardService;
import com.unpeu.service.impl.BoardServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Api("Board 관련 기능")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

    private final IBoardService boardService;

    @ApiOperation(value = "userId의 카테고리에 해당하는 게시글 전체 조회 Controller")
    @RequestMapping(value = "/{userId}/{category}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getBoardListByCategory(
            @NotNull @PathVariable("userId") Long userId, @NotNull @PathVariable("category") String category) {
        logger.info("getBoardListByCategory - 호출");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("boardList", boardService.getBoardList(userId, category));
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "카테고리 리스트 조회 Controller")
    @RequestMapping(value = "/category/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getCategoryList(@NotNull @PathVariable("userId") Long userId) {
        logger.info("getBoardListByCategory - 호출");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("category", boardService.getCategoryList(userId));
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "게시글 상세 조회 Controller")
    @RequestMapping(value = "/{boardId}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getBoardInfo(@NotNull @PathVariable("boardId") Long boardId) {
        logger.info("getBoard - 호출");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("boardInfo", boardService.getBoardInfo(boardId));
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "게시글 추가 Controller")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createBoard(
            @ApiIgnore Authentication authentication, @Valid @RequestBody BoardPostReq board) {
        logger.info("createBoard - 호출");
        Map<String, Object> resultMap = new HashMap<>();

        UnpeuUserDetails userDetails = (UnpeuUserDetails) authentication.getDetails();
        User user =userDetails.getUser();

        resultMap.put("boardInfo", boardService.createBoard(user, board));
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "게시글 수정 Controller")
    @RequestMapping(value = "/{boardId}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> updateBoard(
            @NotNull @PathVariable("boardId") Long boardId, @Valid @RequestBody BoardPostReq board) {
        logger.info("updateBoard - 호출");

        Map<String, Object> resultMap = new HashMap<>();
        if(boardService.updateBoard(boardId, board)) {
            resultMap.put("message", "success");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }
        resultMap.put("message", "fail");
        return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "게시글 삭제 Controller")
    @RequestMapping(value = "/{boardId}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, Object>> deleteBoard(@NotNull @PathVariable("boardId") Long boardId) {
        logger.info("deleteBoard - 호출");

        Map<String, Object> resultMap = new HashMap<>();
        if(boardService.deleteBoard(boardId)) {
            resultMap.put("message", "success");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }
        resultMap.put("message", "fail");
        return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
    }
}
