package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EvaluationRepo extends JpaRepository<Evaluation, Long> {
}
