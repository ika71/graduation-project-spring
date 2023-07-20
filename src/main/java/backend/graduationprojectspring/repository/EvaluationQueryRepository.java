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

    /**
     * member가 생성한 Evaluation을 찾는다<br>
     * EvaluationItem 외래키를 검색 조건으로 사용한다.
     * @param memberId member의 식별 id
     * @param evalItemIdList EvaluationItem의 식별 id 값을 모아둔 List 검색 조건에 사용된다.
     * @return 조회된 Evaluation List
     */
    public List<Evaluation> findByMemberIdEvalItemIdList(String memberId, List<Long> evalItemIdList){
        return queryFactory
                .selectFrom(evaluation)
                .where(evaluation.createdBy.eq(memberId)
                        .and(evaluation.evaluationItem.id.in(evalItemIdList)))
                .fetch();
    }

    /**
     * EvaluationItem별로 그룹화 하여 해당하는 Evaluation.score의 평균을 계산한다.
     * @param evalItemIdList 검색할 EvaluationItem의 식별 키를 모아둔 List
     * @return Map 자료구조로 반환한다.<br>
     * key는 EvaluationItem의 식별키이며 value는 그룹화된 Evaluation.score의 평균이다.
     * <b>Evaluation.score가 없다면 value는 null 된다.</b>
     */
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
}
