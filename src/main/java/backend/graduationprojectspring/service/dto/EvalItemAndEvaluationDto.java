package backend.graduationprojectspring.service.dto;

import lombok.Getter;

/**
 * 하나의 평가요소에 준 평점을 표현하는 dto
 */
@Getter
public class EvalItemAndEvaluationDto {
    private final Long evalItemId; //평가요소 id
    private final String evalItemName; //평가요소 이름
    private final Integer evalScore; //평가요소에 준 평점

    public EvalItemAndEvaluationDto(Long evalItemId, String evalItemName, Integer evalScore) {
        this.evalItemId = evalItemId;
        this.evalItemName = evalItemName;
        this.evalScore = evalScore;
    }
}
