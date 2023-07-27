package backend.graduationprojectspring.repository.query;

import backend.graduationprojectspring.entity.ElectronicDevice;

import java.util.List;
import java.util.Optional;

public interface ElectronicDeviceQueryRepo {
    /**
     * ElectronicDevice를 페이징 조회한다.<br>
     * Category와 fetchJoin을 한다.<br>
     * ElectronicDevice.name으로 정렬된다.
     * @param page 현재 보여줄 페이지 위치
     * @param size 한 페이지의 사이즈
     * @return 조회된 ElectronicDevice List를 반환
     */
    List<ElectronicDevice> pagingJoinCategory(int page, int size);

    /**
     * ElectronicDevice를 페이지 조회한다.<br>
     * Category, EvaluationItem과 fetchjoin한다.<br>
     * EvaluationItem과는 left join을 하기 때문에 join 되는 평가항목이 없어도<br>
     * ElectronicDevice 객체는 존재하며 EvaluationItem은 0개 사이즈의 ArrayList로 가지고 있다.<br>
     * ElectronicDevice.createdTime으로 내림차순 정렬된다
     * @param page 현재 보여줄 페이지 위치
     * @param size 한 페이지의 사이즈
     * @return 조회된 ElectronicDevice List 반환
     */
    List<ElectronicDevice> pagingJoinCategoryAndEvalItem(int page, int size);

    /**
     * ElectronicDevice를 1개 조회한다.
     * Category, EvaluationItem을 fetch join 한다.
     * @param id 조회할 ElectronicDevice의 id
     * @return 조회된 ElectronicDevice 객체<br>
     * 만약 조회된 객체가 없으면 null 반환
     * @throws com.querydsl.core.NonUniqueResultException 조회된 결과가 여러개일 경우
     */
    Optional<ElectronicDevice> findOneJoinCategoryAndEvalItem(Long id);
}
