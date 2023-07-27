package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepo extends JpaRepository<Board, Long> {
}
