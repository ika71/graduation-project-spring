package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.EvaluationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationItemRepository extends JpaRepository<EvaluationItem, Long> {
    List<EvaluationItem> findAllByElectronicDeviceIdOrderByName(Long electronicDeviceId);
    List<EvaluationItem> findAllByElectronicDeviceId(Long electronicDeviceId);
}
