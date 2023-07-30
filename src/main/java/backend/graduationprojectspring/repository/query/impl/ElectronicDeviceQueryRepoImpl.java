package backend.graduationprojectspring.repository.query.impl;

import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.repository.query.ElectronicDeviceQueryRepo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static backend.graduationprojectspring.entity.QCategory.category;
import static backend.graduationprojectspring.entity.QElectronicDevice.electronicDevice;
import static backend.graduationprojectspring.entity.QEvaluationItem.evaluationItem;

@Repository
@RequiredArgsConstructor
public class ElectronicDeviceQueryRepoImpl implements ElectronicDeviceQueryRepo {
    private final JPAQueryFactory queryFactory;

    /**
     * ElectronicDevice를 페이징 조회한다.<br>
     * Category와 fetchJoin을 한다.<br>
     * ElectronicDevice.name으로 정렬된다.
     * @param page 현재 보여줄 페이지 위치
     * @param size 한 페이지의 사이즈
     * @return 조회된 ElectronicDevice List를 반환
     */
    @Override
    public List<ElectronicDevice> pagingJoinCategory(int page, int size){
        return queryFactory
                .selectFrom(electronicDevice)
                .join(electronicDevice.category, category)
                .fetchJoin()
                .orderBy(electronicDevice.name.asc())
                .offset((long)(page - 1)*size)
                .limit(size)
                .fetch();
    }

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
    @Override
    public List<ElectronicDevice> pagingJoinCategoryAndEvalItem(int page, int size){
        /*
        ElectronicDevice와 EvaluationItem은 일대다 관계이기에 페이징과 fetch join을
        2개의 쿼리로 나눠서 처리한다.
         */

        List<ElectronicDevice> deviceList = queryFactory
                .selectFrom(electronicDevice)
                .join(electronicDevice.category, category)
                .fetchJoin()
                .orderBy(electronicDevice.id.desc())
                .offset((long) (page - 1) * size)
                .limit(size)
                .fetch();

        return queryFactory
                .selectFrom(electronicDevice)
                .leftJoin(electronicDevice.evaluationItemList, evaluationItem)
                .fetchJoin()
                .where(electronicDevice.in(deviceList))
                .orderBy(electronicDevice.id.desc())
                .fetch();
    }

    /**
     * ElectronicDevice를 1개 조회한다.
     * Category, EvaluationItem을 fetch join 한다.
     * @param id 조회할 ElectronicDevice의 id
     * @return 조회된 ElectronicDevice 객체<br>
     * 만약 조회된 객체가 없으면 null 반환
     * @throws com.querydsl.core.NonUniqueResultException 조회된 결과가 여러개일 경우
     */
    @Override
    public Optional<ElectronicDevice> findOneJoinCategoryAndEvalItem(Long id){
        ElectronicDevice findDevice = queryFactory
                .selectFrom(electronicDevice)
                .join(electronicDevice.category, category)
                .fetchJoin()
                .leftJoin(electronicDevice.evaluationItemList, evaluationItem)
                .fetchJoin()
                .where(electronicDevice.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(findDevice);
    }
}
