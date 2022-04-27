package com.unpeu.api.diary;

import com.unpeu.domain.request.CommentPostReq;
import com.unpeu.service.iface.ICommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Api("Comment 관련 기능")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private final ICommentService commentService;

    @ApiOperation(value = "댓글 작성 Controller")
    @RequestMapping(value = "/{boardId}", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createComment(
            @NotNull @PathVariable("boardId") Long boardId, @Valid @RequestBody CommentPostReq comment) {

        logger.info("createComment - 호출");
        Map<String, Object> resultMap = new HashMap<>();
        commentService.createComment(boardId, comment);
        resultMap.put("message", "success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "댓글 수정 Controller")
    @RequestMapping(value = "/{commentId}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> updateComment(
            @NotNull @PathVariable("commentId") Long commentId, @Valid @RequestBody CommentPostReq comment) {

        logger.info("updateComment - 호출");
        Map<String, Object> resultMap = new HashMap<>();
        commentService.updateComment(commentId, comment);
        resultMap.put("message", "success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);

    }

    @ApiOperation(value = "댓글 삭제 Controller")
    @RequestMapping(value = "/{commentId}/{password}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, Object>> deleteComment(
            @NotNull @PathVariable("commentId") Long commentId, @NotNull @PathVariable("password") String password) {

        logger.info("deleteComment - 호출");
        Map<String, Object> resultMap = new HashMap<>();
        commentService.deleteComment(commentId, password);
        resultMap.put("message", "success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
