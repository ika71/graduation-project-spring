package backend.graduationprojectspring.service;

import backend.graduationprojectspring.service.dto.EvalItemAndEvaluationDto;

import java.util.List;
import java.util.Map;

public interface EvaluationService {
    /**
     * 이미 있는 평점은 수정
     * 새로운 평점은 저장
     * @param evalItemScoreMap 평가항목 id는 key, 평점 점수는 value
     */
    void put(String memberId, Map<Long, Integer> evalItemScoreMap);

    /**
     * deviceId에 해당하는 전자제품의 평가항목들에 대해<br>
     * memberId에 해당하는 유저가 한 평가 기록을 가져옴
     * @param memberId 평가한 유저의 id
     * @param deviceId 전자제품 id
     * @return 평가항목 id, 평가항목 이름, 평가 점수
     */
    List<EvalItemAndEvaluationDto> findAllByMemberIdAndDeviceId(String memberId, Long deviceId);
}
