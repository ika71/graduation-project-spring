package backend.graduationprojectspring.service.dto;

import lombok.Getter;

@Getter
public class EvalItemAndEvaluationDto {
    private final Long evalItemId;
    private final String evalItemName;
    private final Integer evalScore;

    public EvalItemAndEvaluationDto(Long evalItemId, String evalItemName, Integer evalScore) {
        this.evalItemId = evalItemId;
        this.evalItemName = evalItemName;
        this.evalScore = evalScore;
    }
}
