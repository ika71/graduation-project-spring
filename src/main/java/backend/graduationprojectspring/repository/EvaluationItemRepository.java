package backend.graduationprojectspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationItemRepository extends JpaRepository<EvaluationItemRepository, Long> {
}
