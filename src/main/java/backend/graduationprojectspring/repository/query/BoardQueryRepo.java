package backend.graduationprojectspring.repository.query;

import backend.graduationprojectspring.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardQueryRepo {
    List<Board> paging(int page, int size, Long deviceId);
    Optional<Board> findOneDetail(Long boardId);
}
