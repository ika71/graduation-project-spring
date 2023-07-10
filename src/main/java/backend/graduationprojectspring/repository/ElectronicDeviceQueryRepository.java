package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.ElectronicDevice;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static backend.graduationprojectspring.entity.QCategory.category;
import static backend.graduationprojectspring.entity.QElectronicDevice.electronicDevice;
import static backend.graduationprojectspring.entity.QEvaluationItem.evaluationItem;

@Repository
@RequiredArgsConstructor
public class ElectronicDeviceQueryRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 전자제품 페이지 조회<br>
     * 전자제품 이름으로 정렬됨<br>
     * 전자제품이 속한 카테고리 정보까지 전부 데이터 베이스에서 가져옴
     * @param page 현재 보여줄 페이지 위치
     * @param size 얼마만큼 보여줄지 크기
     * @return 조회된 ElectronicDevice 반환
     */
    public List<ElectronicDevice> pagingFetchJoinCategory(int page, int size){
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
     * 전자제품 페이지 조회<br>
     * 전자제품 이름으로 정렬됨<br>
     * 전자제품이 속한 카테고리, 평가항목 정보까지 전부 데이터 베이스에서 가져옴
     * @param page 현재 보여줄 페이지 위치
     * @param size 얼마만큼 보여줄지 크기
     * @return 조회된 ElectronicDevice 반환
     */
    public List<ElectronicDevice> pagingFetchJoinCategoryAndDevice(int page, int size){
        List<ElectronicDevice> deviceList = queryFactory
                .selectFrom(electronicDevice)
                .join(electronicDevice.category, category)
                .fetchJoin()
                .orderBy(electronicDevice.name.asc())
                .offset((long) (page - 1) * size)
                .limit(size)
                .fetch();

        return queryFactory
                .selectFrom(electronicDevice)
                .leftJoin(electronicDevice.evaluationItemList, evaluationItem)
                .fetchJoin()
                .where(electronicDevice.in(deviceList))
                .orderBy(electronicDevice.name.asc())
                .fetch();
    }

}
