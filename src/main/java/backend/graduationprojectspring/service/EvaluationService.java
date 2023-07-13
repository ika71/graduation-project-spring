package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Evaluation;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.repository.EvaluationItemRepository;
import backend.graduationprojectspring.repository.EvaluationQueryRepository;
import backend.graduationprojectspring.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final EvaluationQueryRepository evalQueryRepository;
    private final EvaluationItemRepository evalItemRepository;

    /**
     * 이미 있는 평점은 수정
     * 새로운 평점은 저장
     * @param evalItemScoreMap 평가항목 id는 key, 평점 점수는 value
     */
    public void put(String memberId, Map<Long, Integer> evalItemScoreMap){
        List<Long> evalItemIdList = new ArrayList<>(evalItemScoreMap.keySet());

        //이미 있는 항목들은 수정
        List<Evaluation> findEvalList = evalQueryRepository
                .findByMemberIdEvalItemIds(memberId, evalItemIdList);
        findEvalList
                .forEach(evaluation -> {
                    Integer updateScore = evalItemScoreMap.get(evaluation.getEvaluationItem().getId());
                    evaluation.update(updateScore);
                    evalItemScoreMap.remove(evaluation.getEvaluationItem().getId()); //이미 수정된 항목은 map에서 제거
                });

        //새로운 항목들은 새로 List 생성 후 데이터베이스에 저장
        List<Evaluation> evaluationList = new ArrayList<>(evalItemScoreMap.size());
        evalItemScoreMap
                .forEach((evalItemId, evalScore)->{
                    Evaluation evaluation = new Evaluation(evalScore);
                    EvaluationItem evalItem = evalItemRepository.getReferenceById(evalItemId);
                    evaluation.setEvaluationItem(evalItem);
                    evaluationList.add(evaluation);
                });
        evaluationRepository.saveAll(evaluationList);
    }
}
