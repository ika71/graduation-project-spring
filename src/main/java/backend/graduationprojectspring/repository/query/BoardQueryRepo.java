package backend.graduationprojectspring.repository.query;

import backend.graduationprojectspring.entity.Board;
import backend.graduationprojectspring.repository.dto.PreviewBoardDto;

import java.util.List;
import java.util.Optional;

public interface BoardQueryRepo {
    /**
     * 하나의 ElectronicDevice에 대한 Board를 페이징 조회 한다.
     * @param page 조회할 페이지 위치
     * @param size 한 페이지의 크기
     * @param deviceId Board들이 관련된 ElectronicDevice의 id
     * @return 조회한 Board List
     */
    List<PreviewBoardDto> paging(int page, int size, Long deviceId);

    /**
     * 하나의 ElectronicDevice에 대한 Board 개수를 조회한다.
     * @param deviceId Board들이 관련된 ElectronicDevice의 id
     * @return 조회한 Board 개수
     */
    Long totalCountByDeviceId(Long deviceId);

    /**
     * Board를 하나 조회 한다.<br>
     * 작성자 정보가 필요하기 때문에 Member와 join 한다.<br>
     * 게시글에 올라간 이미지가 필요하기 때문에 Image와 left join한다.
     * @param boardId 조회할 board의 id
     * @return 조회한 Board를 Optinal로 반환한다.
     */
    Optional<Board> findOneDetail(Long boardId);
}
