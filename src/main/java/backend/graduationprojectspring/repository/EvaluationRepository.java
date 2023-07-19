package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.Evaluation;
import backend.graduationprojectspring.entity.EvaluationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findAllByCreatedByAndEvaluationItemIn(String createdBy, List<EvaluationItem> evaluationItem);
}
