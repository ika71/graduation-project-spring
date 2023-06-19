package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.EvaluationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationItemRepository extends JpaRepository<EvaluationItem, Long> {
}
