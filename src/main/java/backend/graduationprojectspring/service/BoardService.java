package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Board;

import java.util.List;

public interface BoardService {
    List<Board> paging(int page, int size, Long deviceId);
    Long totalCount();
    Board create(String title, String content, Long deviceId);
}
