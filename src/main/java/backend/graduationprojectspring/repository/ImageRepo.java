package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepo extends JpaRepository<Image, Long> {
    long countByBoardId(Long boardId);
}
