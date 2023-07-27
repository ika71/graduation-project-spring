package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.EvaluationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationItemRepo extends JpaRepository<EvaluationItem, Long> {
    /**
     * ElectronicDeviceId를 외래키로 갖는 모든 EvaluationItem 객체를 찾는다<br>
     * EvaluationItem.name으로 정렬된다.
     * @param electronicDeviceId 검색 조건으로 사용할 ElectronicDeviceId
     * @return 조회된 EvaluationItem List 반환
     */
    List<EvaluationItem> findAllByElectronicDeviceIdOrderByName(Long electronicDeviceId);

    /**
     * ElectronicDeviceId를 외래키로 갖는 모든 EvaluationItem 객체를 찾는다
     * @param electronicDeviceId 검색 조건으로 사용할 ElectronicDeviceId
     * @return 조회된 EvaluationItem List 반환
     */
    List<EvaluationItem> findAllByElectronicDeviceId(Long electronicDeviceId);
    boolean existsByNameAndElectronicDeviceId(String name, Long deviceId);
}
