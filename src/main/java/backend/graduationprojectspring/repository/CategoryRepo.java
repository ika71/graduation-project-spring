package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepo extends JpaRepository<Category, Long> {
    List<Category> findAllByOrderByName();
    boolean existsByName(String name);
}
