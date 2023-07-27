package backend.graduationprojectspring.repository.query;

import backend.graduationprojectspring.entity.ElectronicDevice;

import java.util.List;
import java.util.Optional;

public interface ElectronicDeviceQueryRepo {
    /**
     * ElectronicDevice를 페이징 조회한다.<br>
     * Category와 join을 한다.<br>
     * @param page 현재 보여줄 페이지 위치
     * @param size 한 페이지의 사이즈
     * @return 조회된 ElectronicDevice List를 반환
     */
    List<ElectronicDevice> pagingJoinCategory(int page, int size);

    /**
     * ElectronicDevice를 페이지 조회한다.<br>
     * Category와 join한다.<br>
     * EvaluationItem과는 left join 한다.
     * @param page 현재 보여줄 페이지 위치
     * @param size 한 페이지의 사이즈
     * @return 조회된 ElectronicDevice List 반환
     */
    List<ElectronicDevice> pagingJoinCategoryAndEvalItem(int page, int size);

    /**
     * ElectronicDevice를 1개 조회한다.
     * Category와 join 한다.
     * EvaluationItem과 left join 한다.
     * @param id 조회할 ElectronicDevice의 id
     * @return 조회된 ElectronicDevice 객체를 Optional로 반환한다.
     */
    Optional<ElectronicDevice> findOneJoinCategoryAndEvalItem(Long id);
}
