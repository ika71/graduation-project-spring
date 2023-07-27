package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.BoardComment;

import java.util.List;

public interface BoardCommentService {
    List<BoardComment> paging(int page, int size, Long boardId);
    Long totalCountByBoardId(Long boardId);
    BoardComment create(String content, Long boardId, Long memberId);
    void delete(Long id, Long requestMemberId);
}
