package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCommentRepo extends JpaRepository<BoardComment, Long> {
}
