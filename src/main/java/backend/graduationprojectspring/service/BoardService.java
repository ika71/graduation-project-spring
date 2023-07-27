package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Board;
import backend.graduationprojectspring.exception.NotExistsException;

import java.util.List;

public interface BoardService {
    /**
     * 하나의  전자제품에 대한 게시글을 페이지 조회한다.
     * @param page 조회할 페이지의 위치
     * @param size 한 페이지의 크기
     * @param deviceId 게시글들이 관련된 전자제품의 id
     * @return 조회한 게시글 List
     */
    List<Board> paging(int page, int size, Long deviceId);
    /**
     * 하나의 전자제품에 대한 게시글들의 개수
     * @param deviceId 게시글들이 관련된 전자제품의 id
     * @return 조회한 게시글 개수
     */
    Long totalCountByDeviceId(Long deviceId);

    /**
     * 하나의 게시글을 조회한다. 게시글의 조회 수는 하나 늘어나야 한다.<br>
     * member와 join 한다
     * @param id 조회할 게시글의 id
     * @return 조회한 게시글
     */
    Board findOneDetail(Long id);
    /**
     * 게시글을 생성한다.
     * @param title 게시글 제목
     * @param content 게시글 내용
     * @param deviceId 게시글이 기록되는 전자제품의 id
     * @param memberId 게시글을 생성하는 member의 id
     * @return 생성된 게시글 반환
     */
    Board create(String title, String content, Long deviceId, Long memberId);

    /**
     * 게시 글을 수정한다.
     * @param boardId 수정할 게시글의 id
     * @param title 수정된 제목
     * @param content 수정된 내용
     * @param requestMemberId 수정을 요청하는 member의 id
     * @throws NotExistsException 수정할 게시글이 존재하지 않으면 발생
     */
    void update(Long boardId, String title, String content, Long requestMemberId);

    /**
     * 게시글을 삭제한다.
     * @param boardId 삭제할 게시글의 id
     * @param requestMemberId 삭제를 요청하는 member의 id
     * @throws NotExistsException 삭제할 게시글이 존재하지 않으면 발생
     */
    void delete(Long boardId, Long requestMemberId);
}
