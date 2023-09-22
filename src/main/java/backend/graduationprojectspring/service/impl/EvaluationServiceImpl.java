package backend.graduationprojectspring.service.impl;

import backend.graduationprojectspring.entity.Evaluation;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.repository.EvaluationItemRepo;
import backend.graduationprojectspring.repository.EvaluationRepo;
import backend.graduationprojectspring.repository.query.EvaluationQueryRepo;
import backend.graduationprojectspring.service.EvaluationService;
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
public class EvaluationServiceImpl implements EvaluationService {
    private final EvaluationRepo evaluationRepo;
    private final EvaluationQueryRepo evalQueryRepo;
    private final EvaluationItemRepo evalItemRepository;

    @Override
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

    @Override
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
