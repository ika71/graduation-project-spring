package backend.graduationprojectspring.repository.query;

import backend.graduationprojectspring.entity.BoardComment;

import java.util.List;

public interface BoardCommentQueryRepo {
    /**
     * 하나의 Board에 대한 BoardComment를 페이지 조회한다.
     * @param page 조회할 페이지의 위치
     * @param size 한 페이지의 크기
     * @param boardId BoardComment들이 관련된 Board의 id
     * @return 조회한 BoardComment List
     */
    List<BoardComment> paging(int page, int size, Long boardId);
}
