package backend.graduationprojectspring.repository.query.impl;

import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.repository.query.ElectronicDeviceQueryRepo;
import com.querydsl.core.types.dsl.BooleanExpression;
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

    @Override
    public List<ElectronicDevice> pagingJoinCategoryAndEvalItem(int page, int size, String nameCondition, String categoryCondition){
        List<ElectronicDevice> deviceList = queryFactory
                .selectFrom(electronicDevice)
                .join(electronicDevice.category, category)
                .fetchJoin()
                .where(deviceNameEq(nameCondition), categoryEq(categoryCondition))
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

    @Override
    public Long countByCondition(String nameCondition, String categoryCondition){
        return queryFactory
                .select(electronicDevice.count())
                .from(electronicDevice)
                .where(deviceNameEq(nameCondition), categoryEq(categoryCondition))
                .fetchOne();
    }

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

    private BooleanExpression deviceNameEq(String deviceName) {
        return deviceName != null ? electronicDevice.name.contains(deviceName) : null;
    }
    private BooleanExpression categoryEq(String category) {
        return category != null ? electronicDevice.category.name.contains(category) : null;
    }
}
