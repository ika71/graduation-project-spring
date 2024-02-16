package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.BoardComment;
import backend.graduationprojectspring.exception.HttpError;

import java.util.List;

public interface BoardCommentService {
    /**
     * 하나의  게시 글에 대한 게시글 댓글을 페이지 조회한다.
     * @param page 조회할 페이지의 위치
     * @param size 한 페이지의 크기
     * @param boardId 댓글들이 관련된 게시글의 id
     * @return 조회한 댓글 List
     */
    List<BoardComment> paging(int page, int size, Long boardId);
    /**
     * 하나의 게시 글에 대한 댓글의 개수
     * @param boardId 댓글들이 관련된 게시글의 id
     * @return 조회한 댓글 개수
     */
    long totalCountByBoardId(Long boardId);

    /**
     * 댓글을 생성한다.
     * @param comment 댓글 내용
     * @param boardId 댓글이 생성될 게시글의 id
     * @param memberId 댓글을 생성하는 member의 id
     * @return 생성된 댓글 반환
     */
    BoardComment create(String comment, Long boardId, Long memberId);

    /**
     * 댓글을 삭제한다.
     * @param id 삭제할 댓글 id
     * @param requestMemberId 삭제를 요청하는 Member의 id
     * @throws HttpError 대상이 되는 댓글이 존재하지 않는다면 발생
     * @throws HttpError 자신이 작성한 댓글이 아니면 발생
     */
    void delete(Long id, Long requestMemberId) throws HttpError;
}
