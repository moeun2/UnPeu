package com.unpeu.api.diary;

import com.unpeu.config.auth.UnpeuUserDetails;
import com.unpeu.config.exception.ApplicationException;
import com.unpeu.config.media.MediaService;
import com.unpeu.domain.entity.User;
import com.unpeu.domain.request.BoardPostReq;
import com.unpeu.service.iface.IBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    private final IBoardService boardService;
    private final MediaService mediaService;

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
    public ResponseEntity<Map<String, Object>> createBoard (
            @ApiIgnore Authentication authentication, @Valid @RequestBody BoardPostReq board) {
        logger.info("createBoard - 호출");
        Map<String, Object> resultMap = new HashMap<>();

        UnpeuUserDetails userDetails = (UnpeuUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        boardService.createBoard(user, board);

        resultMap.put("message", "success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "게시글 수정 Controller")
    @RequestMapping(value = "/{boardId}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> updateBoard(@ApiIgnore Authentication authentication,
            @NotNull @PathVariable("boardId") Long boardId, @Valid @RequestBody BoardPostReq board) {
        logger.info("updateBoard - 호출");
        Map<String, Object> resultMap = new HashMap<>();

        UnpeuUserDetails userDetails = (UnpeuUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        try {
            boardService.updateBoard(user, boardId, board);
            resultMap.put("message", "success");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);

        } catch (ApplicationException e) {
            // 권한이 없는 사용자가 게시글을 수정하는 경우
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.FORBIDDEN); // 403
        }
    }

    @ApiOperation(value = "게시글 삭제 Controller")
    @RequestMapping(value = "/{boardId}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, Object>> deleteBoard(
            @ApiIgnore Authentication authentication, @NotNull @PathVariable("boardId") Long boardId) {
        logger.info("deleteBoard - 호출");
        Map<String, Object> resultMap = new HashMap<>();

        UnpeuUserDetails userDetails = (UnpeuUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        try {
            boardService.deleteBoard(user, boardId);
            resultMap.put("message", "success");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);

        } catch (ApplicationException e) {
            // 권한이 없는 사용자가 게시글을 삭제하는 경우
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.FORBIDDEN); // 403
        }
    }

    @ApiOperation(value = "게시글 이미지 추가 Controller")
    @RequestMapping(value = "/addImg", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, Object>> addBoardFileImage(@RequestPart(value = "file") @NotNull MultipartFile upImage) {
        logger.info("addBoardFileImage - 호출");
        Map<String, Object> resultMap = new HashMap<>();

        // 이미지 -> url로 변경 설정
        String url = mediaService.save(upImage);
        logger.info("media Saved Url : " + url);

        resultMap.put("message", "success");
        resultMap.put("url", url);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "게시글 이미지 삭제 Controller")
    @RequestMapping(value = "/deleteImg", produces = "application/json; charset=UTF8", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> deleteBoardFileImage(@Valid @RequestBody String imgUrl) {
        logger.info("deleteBoardFileImage - 호출");
        Map<String, Object> resultMap = new HashMap<>();
        mediaService.delete(imgUrl);
        resultMap.put("message", "success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
