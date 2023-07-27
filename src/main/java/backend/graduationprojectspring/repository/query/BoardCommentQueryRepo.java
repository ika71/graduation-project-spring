package backend.graduationprojectspring.repository.query;

import backend.graduationprojectspring.entity.BoardComment;

import java.util.List;

public interface BoardCommentQueryRepo {
    List<BoardComment> paging(int page, int size, Long boardId);
    Long totalCountByBoardId(Long boardId);
}
