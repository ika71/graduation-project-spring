package backend.graduationprojectspring.repository.query;

import backend.graduationprojectspring.entity.Evaluation;

import java.util.List;
import java.util.Map;

public interface EvaluationQueryRepo {
    /**
     * EvaluationItem에서 member가 생성한 Evaluation을 찾는다.<br>
     * @param memberId member의 식별 id
     * @param evalItemIdList EvaluationItem의 id 값을 모아둔 List.
     * @return 조회된 Evaluation List
     */
    List<Evaluation> findByMemberIdEvalItemIdList(String memberId, List<Long> evalItemIdList);

    /**
     * EvaluationItem별로 그룹화 하여 해당하는 Evaluation.score의 평균을 계산한다.
     * @param evalItemIdList 검색할 EvaluationItem의 식별 키를 모아둔 List
     * @return Map 자료구조로 반환한다.
     * key는 EvaluationItem의 식별키이며 value는 그룹화된 Evaluation.score의 평균이다.
     * <b>Evaluation.score가 없다면 value는 null 된다.</b>
     */
    Map<Long, Double> avgGroupByEvalItem(List<Long> evalItemIdList);
}
