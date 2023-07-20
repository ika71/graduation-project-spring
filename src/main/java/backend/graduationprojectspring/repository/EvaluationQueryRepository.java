package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.Evaluation;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static backend.graduationprojectspring.entity.QEvaluation.evaluation;

@Repository
@RequiredArgsConstructor
public class EvaluationQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Evaluation> findByMemberIdEvalItemIds(String memberId, List<Long> evalItemIdList){
        return queryFactory
                .selectFrom(evaluation)
                .where(evaluation.createdBy.eq(memberId)
                        .and(evaluation.evaluationItem.id.in(evalItemIdList)))
                .fetch();
    }
    public Map<Long, Double> avgGroupByEvalItem(List<Long> evalItemIdList){
        List<Tuple> fetchList = queryFactory
                .select(evaluation.evaluationItem.id,
                        evaluation.evaluationScore.avg())
                .from(evaluation)
                .where(evaluation.evaluationItem.id.in(evalItemIdList))
                .groupBy(evaluation.evaluationItem)
                .fetch();

        Map<Long, Double> avgGroupByEvalItemMap = new HashMap<>(fetchList.size());

        for (Tuple fetch : fetchList) {
            Long evalItemId = fetch.get(evaluation.evaluationItem.id);
            Double evalItemAvg = fetch.get(evaluation.evaluationScore.avg());
            avgGroupByEvalItemMap.put(evalItemId, evalItemAvg);
        }
        return avgGroupByEvalItemMap;
    }
}
