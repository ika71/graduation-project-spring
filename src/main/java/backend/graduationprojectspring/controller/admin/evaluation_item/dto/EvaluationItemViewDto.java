package backend.graduationprojectspring.controller.admin.evaluation_item.dto;

import backend.graduationprojectspring.entity.EvaluationItem;
import lombok.Getter;

@Getter
public class EvaluationItemViewDto {
    private final Long id;
    private final String name;

    public EvaluationItemViewDto(EvaluationItem evaluationItem) {
        this.id = evaluationItem.getId();
        this.name = evaluationItem.getName();
    }
}
