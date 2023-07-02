package backend.graduationprojectspring.controller.admin.evaluation_item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EvaluationItemUpdateDto {
    @NotBlank
    private String name;


}
