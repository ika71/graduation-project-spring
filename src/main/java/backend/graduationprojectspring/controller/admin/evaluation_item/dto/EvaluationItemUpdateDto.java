package backend.graduationprojectspring.controller.admin.evaluation_item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class EvaluationItemUpdateDto {
    @NotNull
    private Long id;
    @NotBlank
    private String name;


}
