package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.EvaluationItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EvaluationItemRepo extends JpaRepository<EvaluationItem, Long> {
    List<EvaluationItem> findAllByElectronicDeviceIdOrderByName(Long electronicDeviceId);
    List<EvaluationItem> findAllByElectronicDeviceId(Long electronicDeviceId);
    boolean existsByNameAndElectronicDeviceId(String name, Long deviceId);
}
