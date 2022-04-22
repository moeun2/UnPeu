package com.unpeu.service.iface;

import com.unpeu.domain.entity.Board;
import com.unpeu.domain.entity.User;
import com.unpeu.domain.request.BoardPostReq;
import com.unpeu.domain.response.BoardGetRes;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IBoardService {

    List<String> getCategoryList(Long userId);

    List<BoardGetRes> getBoardList(Long userId, String category);

    BoardGetRes getBoardInfo(Long boardId);

    @Transactional(rollbackFor = Exception.class)
    Board createBoard(User user, BoardPostReq board);

    @Transactional(rollbackFor = Exception.class)
    boolean updateBoard(Long boardId, BoardPostReq boardPostReq);

    @Transactional(rollbackFor = Exception.class)
    boolean deleteBoard(Long boardId);
}
