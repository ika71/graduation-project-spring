package backend.graduationprojectspring.service.impl;

import backend.graduationprojectspring.entity.Evaluation;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.repository.EvaluationItemRepo;
import backend.graduationprojectspring.repository.EvaluationRepo;
import backend.graduationprojectspring.repository.query.EvaluationQueryRepo;
import backend.graduationprojectspring.service.dto.EvalItemAndEvaluationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationService {
    private final EvaluationRepo evaluationRepo;
    private final EvaluationQueryRepo evalQueryRepo;
    private final EvaluationItemRepo evalItemRepository;

    /**
     * 이미 있는 평점은 수정
     * 새로운 평점은 저장
     * @param evalItemScoreMap 평가항목 id는 key, 평점 점수는 value
     */
    public void put(String memberId, Map<Long, Integer> evalItemScoreMap){
        List<Long> evalItemIdList = new ArrayList<>(evalItemScoreMap.keySet());

        //이미 있는 항목들은 수정
        List<Evaluation> findEvalList = evalQueryRepo
                .findByMemberIdEvalItemIdList(memberId, evalItemIdList);
        for (Evaluation evaluation : findEvalList) {
            Integer updateScore = evalItemScoreMap.get(evaluation.getEvaluationItem().getId());
            evaluation.updateScore(updateScore);
            evalItemScoreMap.remove(evaluation.getEvaluationItem().getId()); //이미 수정된 항목은 map에서 제거
        }

        //새로운 항목들은 새로 List 생성 후 데이터베이스에 저장
        List<Evaluation> evaluationList = new ArrayList<>(evalItemScoreMap.size());
        for (Map.Entry<Long, Integer> entry : evalItemScoreMap.entrySet()) {
            Long evalItemId = entry.getKey();
            Integer evalScore = entry.getValue();
            EvaluationItem evalItem = evalItemRepository.getReferenceById(evalItemId);
            evaluationList.add(new Evaluation(evalScore, evalItem));
        }
        evaluationRepo.saveAll(evaluationList);
    }

    /**
     * deviceId에 해당하는 전자제품의 평가항목들에 대해<br>
     * memberId에 해당하는 유저가 한 평가 기록을 가져옴
     * @param memberId 평가한 유저의 id
     * @param deviceId 전자제품 id
     * @return 평가항목 id, 평가항목 이름, 평가 점수
     */
    @Transactional(readOnly = true)
    public List<EvalItemAndEvaluationDto> findAllByMemberIdAndDeviceId(String memberId, Long deviceId){
        List<EvaluationItem> evalItemList = evalItemRepository.findAllByElectronicDeviceId(deviceId);
        List<Long> evalItemIdList = evalItemList
                .stream()
                .map(EvaluationItem::getId)
                .toList();

        List<Evaluation> evalList = evalQueryRepo
                .findByMemberIdEvalItemIdList(
                        memberId,
                        evalItemIdList);

        //키 = 평가항목 id, 값 = 유저가 쓴 평점(null 가능)
        Map<Long, Integer> evalMap = new HashMap<>();

        for (Evaluation eval : evalList) {
            evalMap.put(eval.getEvaluationItem().getId(),
                    eval.getEvaluationScore());
        }

        return evalItemList
                .stream()
                .map(evalItem -> new EvalItemAndEvaluationDto(
                        evalItem.getId(),
                        evalItem.getName(),
                        evalMap.get(evalItem.getId())))
                .toList();
    }
}
