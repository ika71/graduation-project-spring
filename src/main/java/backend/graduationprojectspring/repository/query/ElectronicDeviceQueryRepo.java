package backend.graduationprojectspring.repository.query;

import backend.graduationprojectspring.entity.ElectronicDevice;

import java.util.List;
import java.util.Optional;

public interface ElectronicDeviceQueryRepo {
    /**
     * ElectronicDeivce를 Category와 조인한 후 페이징 한다.
     * @param page 페이지 위치
     * @param size 한 페이지의 크기
     * @return 페이지 조회한 ElectronicDevice 리스트
     */
    List<ElectronicDevice> pagingJoinCategory(int page, int size);

    /**
     * ElectronicDeivce를 Category, EvaluationItem과 조인한 후 페이징 한다.<br>
     * 페이징은 ElectronicDeivce를 기준으로 한다. 일대다 조인으로 인한 뻥튀기 데이터는 고려하지 않는다.
     * @param page 페이지 위치
     * @param size 한 페이지의 크기
     * @return 페이지 조회한 ElectronicDevice 리스트
     */
    List<ElectronicDevice> pagingJoinCategoryAndEvalItem(int page, int size);

    /**
     * ElectronicDevice를 하나 조회한다.<br>
     * ElectronicDevice는 Category와 EvaluationItem과 조인한다.
     * @param id 조회할 ElectronicDevice의 id 값
     * @return 조회한 ElectronicDevice를 Optional로 반환한다.
     */
    Optional<ElectronicDevice> findOneJoinCategoryAndEvalItem(Long id);
}
