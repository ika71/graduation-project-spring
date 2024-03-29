package backend.graduationprojectspring.repository.query;

import backend.graduationprojectspring.entity.ElectronicDevice;

import java.util.List;
import java.util.Optional;

public interface ElectronicDeviceQueryRepo {
    /**
     * ElectronicDevice를 페이징 조회한다.<br>
     * Category와 join을 한다.<br>
     * 이미지와 left join 한다.
     * @param page 현재 보여줄 페이지 위치
     * @param size 한 페이지의 사이즈
     * @return 조회된 ElectronicDevice List를 반환
     */
    List<ElectronicDevice> pagingJoinCategory(int page, int size);

    /**
     * ElectronicDevice를 페이지 조회한다.<br>
     * Category와 join한다.<br>
     * EvaluationItem과는 left join 한다.<br>
     * 이미지와 left join 한다.
     * @param page 현재 보여줄 페이지 위치
     * @param size 한 페이지의 사이즈
     * @param nameCondition 이름으로 검색하고 싶을 때 사용, null이면 검색조건 없음
     * @param categoryCondition 카테고리로 검색하고 싶을 때 사용, null이면 검색조건 없음
     * @return 조회된 ElectronicDevice List 반환
     */
    List<ElectronicDevice> pagingJoinCategoryAndEvalItem(int page, int size, String nameCondition, String categoryCondition);

    /**
     * ElectronicDevice를 1개 조회한다.
     * Category와 join 한다. <br>
     * EvaluationItem과 left join 한다. <br>
     * 이미지와 left join 한다.
     * @param id 조회할 ElectronicDevice의 id
     * @return 조회된 ElectronicDevice 객체를 Optional로 반환한다.
     */
    Optional<ElectronicDevice> findOneJoinCategoryAndEvalItem(Long id);

    /**
     * 검색 조건이 들어간 카운트 쿼리
     * @param nameCondition 이름으로 검색하고 싶을 때 사용, null이면 검색조건 없음
     * @param categoryCondition 카테고리로 검색하고 싶을 때 사용, null이면 검색조건 없음
     * @return 검색 조건에 걸린 ElectronicDevice 개수
     */
    Long countByCondition(String nameCondition, String categoryCondition);
}
