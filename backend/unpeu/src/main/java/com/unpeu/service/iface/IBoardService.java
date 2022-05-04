package com.unpeu.service.iface;

import com.unpeu.domain.entity.Board;
import com.unpeu.domain.entity.User;
import com.unpeu.domain.request.BoardPostReq;
import com.unpeu.domain.response.BoardGetRes;
import com.unpeu.domain.response.BoardInfoGetRes;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IBoardService {

    List<String> getCategoryList(Long userId);

    List<BoardGetRes> getBoardList(Long userId, String category);

    BoardInfoGetRes getBoardInfo(Long boardId);

    Board createBoard(User user, BoardPostReq board);

    void updateBoard(User user, Long boardId, BoardPostReq boardPostReq);

    void deleteBoard(User user, Long boardId);
}
