package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Board;

import java.util.List;

public interface BoardService {
    List<Board> paging(int page, int size, Long deviceId);
    Long totalCountByDeviceId(Long deviceId);
    Board findOneDetail(Long id);
    Board create(String title, String content, Long deviceId);

    void update(Long boardId, String title, String content);
    void delete(Long boardId);
}
