package backend.graduationprojectspring.repository.query.impl;

import backend.graduationprojectspring.entity.Evaluation;
import backend.graduationprojectspring.repository.query.EvaluationQueryRepo;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static backend.graduationprojectspring.entity.QEvaluation.evaluation;
import static backend.graduationprojectspring.entity.QEvaluationItem.evaluationItem;

@Repository
@RequiredArgsConstructor
public class EvaluationQueryRepoImpl implements EvaluationQueryRepo {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Evaluation> findByMemberIdEvalItemIdList(String memberId, List<Long> evalItemIdList){
        return queryFactory
                .selectFrom(evaluation)
                .where(evaluation.createdBy.eq(memberId)
                        .and(evaluation.evaluationItem.id.in(evalItemIdList)))
                .fetch();
    }

    @Override
    public Map<Long, Double> avgGroupByEvalItem(List<Long> evalItemIdList){
        List<Tuple> fetchList = queryFactory
                .select(evaluation.evaluationItem.id,
                        evaluation.evaluationScore.avg())
                .from(evaluation)
                .where(evaluation.evaluationItem.id.in(evalItemIdList))
                .groupBy(evaluation.evaluationItem)
                .fetch();

        //조회된 evaluationItemId, evaluationScore 평균을 맵으로 변환한다.
        Map<Long, Double> avgGroupByEvalItemMap = new HashMap<>(fetchList.size());

        for (Tuple fetch : fetchList) {
            Long evalItemId = fetch.get(evaluation.evaluationItem.id);
            Double evalItemAvg = fetch.get(evaluation.evaluationScore.avg());
            avgGroupByEvalItemMap.put(evalItemId, evalItemAvg);
        }
        return avgGroupByEvalItemMap;
    }

    @Override
    public Map<Long, Double> avgGroupByDevice(List<Long> deviceIdList) {
        List<Tuple> fetchList = queryFactory
                .select(evaluationItem.electronicDevice.id,
                        evaluation.evaluationScore.avg())
                .from(evaluation)
                .join(evaluation.evaluationItem, evaluationItem)
                .where(evaluationItem.electronicDevice.id.in(deviceIdList))
                .groupBy(evaluationItem.electronicDevice)
                .fetch();

        //조회된 electronicDeviceId, evaluationScore 평균을 맵으로 변환한다.
        Map<Long, Double> avgGroupByDeviceMap = new HashMap<>(fetchList.size());
        for (Tuple fetch : fetchList) {
            Long deviceId = fetch.get(evaluationItem.electronicDevice.id);
            Double deviceAvg = fetch.get(evaluation.evaluationScore.avg());
            avgGroupByDeviceMap.put(deviceId, deviceAvg);
        }
        return avgGroupByDeviceMap;
    }
}
