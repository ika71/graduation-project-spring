package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepo extends JpaRepository<Category, Long> {
    /**
     * 모든 Category를 이름으로 정렬하여 조회한다.
     * @return 정렬된 category List를 반환한다.
     */
    List<Category> findAllByOrderByName();
    boolean existsByName(String name);
}
